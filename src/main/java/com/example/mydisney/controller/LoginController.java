package com.example.mydisney.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mydisney.entity.Userinfo;
import com.example.mydisney.service.WxApi;
import com.example.mydisney.tool.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/login")
public class LoginController {

    @Autowired
    private WxApi wxApi;

    @Autowired
    private AES aes;

    @Value("${aes.pwd}")
    private String pwd;


    @GetMapping("/login")
    public Map<String, Object> login(String code) {
        JSONObject jsonObject = wxApi.authCode2Session(code);
        Map<String, Object> result = new HashMap<>();
        result.put("openid", jsonObject.get("openid"));
        result.put("sessionKey", jsonObject.get("session_key"));
        return result;
    }

    @GetMapping("/userLogin")
    public Map<String, Object> getUser(String code, String loginCode) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(loginCode)) {
            return null;
        }
        JSONObject jsonBySession = wxApi.authCode2Session(loginCode);
        Map<String, Object> result = new HashMap<>();
        if (jsonBySession != null && jsonBySession.get("openid") != null) {
            String openid = (String) jsonBySession.get("openid");
            Userinfo userInfoByMapper = wxApi.getUserInfoByMapper(openid);
            Userinfo userinfo = userInfoByMapper;
            if (userInfoByMapper == null || StringUtils.isEmpty(userInfoByMapper.getOpenid())) {
                JSONObject userInfo = wxApi.getUserInfo(code);
                if (userInfo == null || !userInfo.get("errcode").equals(0)) {
                    return null;
                }
                userinfo = wxApi.saveUserInfo(userInfo, jsonBySession);
            }
            result.put("userid", AES.encode(openid, pwd));
            result.put("name", userinfo.getUserName());
            result.put("icon", userinfo.getIcon());
            result.put("phone", userinfo.getPurePhoneNumber());
        }
        result.put("success", "OK");
        return result;
    }


    @GetMapping("/getUserByOpenId")
    public String getUserByOpenId(String code) {
        Userinfo userInfoByMapper = wxApi.getUserInfoByMapper(AES.decode(code, pwd));
        if (userInfoByMapper != null && !StringUtils.isEmpty(userInfoByMapper.getOpenid())) {
            userInfoByMapper.setOpenid(AES.encode(userInfoByMapper.getOpenid(), pwd));
            return JSON.toJSONString(userInfoByMapper);
        }
        return null;
    }
}
