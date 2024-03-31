package com.example.mydisney.entity;


import lombok.Data;

@Data
public class Userinfo {

    private Integer id;
    private String userName;
    private String icon;
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Integer timestamp;
    private String appid;

    private String openid;

    private String unionid;

}
