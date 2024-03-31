package com.example.mydisney.service;

import com.alibaba.fastjson.JSONObject;
import com.example.mydisney.entity.Business;
import com.example.mydisney.entity.Userinfo;

import java.util.List;

public interface WxApi {

    /**
     * 获取code
     * @param jsCode
     * @return
     */
    public JSONObject authCode2Session(String jsCode);

    /**
     * 获取用户信息
     * @param code
     * @return
     */
    public JSONObject getUserInfo(String code);



    /**
     * 保存用户信息
     * @param userInfo
     */
    public Userinfo saveUserInfo(JSONObject userInfo,JSONObject jsonBySession);

    /**
     * 获取营业时间
     * @return
     */
    public JSONObject getBusiness();

    /**
     * 保存营业时间
     */
    public void saveBusiness(Business business);


    public Userinfo getUserInfoByMapper(String openId);

}
