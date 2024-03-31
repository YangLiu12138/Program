package com.example.mydisney.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mydisney.mapper.BussinessMapper;
import com.example.mydisney.mapper.UserinfoMapper;
import com.example.mydisney.entity.Business;
import com.example.mydisney.entity.Userinfo;
import com.example.mydisney.service.WxApi;
import com.example.mydisney.tool.DateUtil;
import com.example.mydisney.tool.WxAppConfigName;
import com.example.mydisney.tool.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxApiImpl implements WxApi {

    @Autowired
    private WxAppConfigName wxAppConfigName;
    @Autowired
    private WxUtil wxUtil;
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private BussinessMapper bussinessMapper;

    @Override
    public JSONObject authCode2Session(String jsCode) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxAppConfigName.getAppid() + "&secret=" + wxAppConfigName.getAppsecret() + "&js_code=" + jsCode + "&grant_type=authorization_code";
        String str = wxUtil.URLConnection(url, "GET", null);
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            return JSONObject.parseObject(str);
        }
    }

    @Override
    public JSONObject getUserInfo(String code) {
        //拼接微信官方的url来获取access_token
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxAppConfigName.getAppid() + "&secret=" + wxAppConfigName.getAppsecret();
        String urlResult = wxUtil.URLConnection(tokenUrl, "GET", null);
        //转为json
        JSONObject jsonObject = JSON.parseObject(urlResult);
        String access_token = jsonObject.getString("access_token");
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + access_token;
        JSONObject params = new JSONObject();
        params.put("code", code);
        String str = wxUtil.URLConnection(url, "POST", params);
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            return JSONObject.parseObject(str);
        }
    }

    @Override
    public Userinfo saveUserInfo(JSONObject userJson, JSONObject jsonBySession) {
        Userinfo userinfo = new Userinfo();
        JSONObject phoneInfo = (JSONObject) userJson.get("phone_info");
        userinfo.setPhoneNumber((String) phoneInfo.get("phoneNumber"));
        userinfo.setPurePhoneNumber((String) phoneInfo.get("purePhoneNumber"));
        userinfo.setCountryCode((String) phoneInfo.get("countryCode"));
        JSONObject watermark = (JSONObject) phoneInfo.get("watermark");
        userinfo.setTimestamp((Integer) watermark.get("timestamp"));
        userinfo.setAppid((String) watermark.get("appid"));
        userinfo.setOpenid((String) jsonBySession.get("openid"));
        userinfo.setUnionid((String) jsonBySession.get("unionid"));
        userinfoMapper.insert(userinfo);
        return userinfo;
    }

    @Override
    public JSONObject getBusiness() {
        String currentDate = dateUtil.getCurrentDate(null);
        Business business = bussinessMapper.queryByCurrTime(currentDate);
        return JSONObject.parseObject(JSON.toJSONString(business));
    }

    public void saveBusiness(Business business) {
        bussinessMapper.saveBussiness(business);
    }

    @Override
    public Userinfo getUserInfoByMapper(String openId) {
        Map<String, Object> param = new HashMap<>();
        param.put("openId", openId);
        List<Userinfo> userinfos = userinfoMapper.selectByMap(param);
        if (userinfos != null && userinfos.size() > 0) {
            return userinfos.get(0);
        }
        return null;
    }



}
