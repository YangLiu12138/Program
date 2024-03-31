package com.example.mydisney.tool;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WxAppConfigName {
    @Value("${wx.miniapp.appid}")
    private String appid;
    @Value("${wx.miniapp.appsecret}")
    private String appsecret;

    @Value("${gd.key}")
    private String gdKey;
    // getters and setters

    @Value("${gd.city}")
    private String gdcity;
}