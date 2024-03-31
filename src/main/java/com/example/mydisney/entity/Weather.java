package com.example.mydisney.entity;


import lombok.Data;

@Data
public class Weather {

    private Integer id;
    private String province;
    private String city;
    private long adcode;
    private String weather;
    private String temperature;
    private String winddirection;
    private String windpower;
    private String dayweather;
    private String nightweather;
    private String daytemp;
    private String nighttemp;
    private String daywind;
    private String nightwind;
    private String date;
    private String isnow;
    private String daypower;
    private String nightpower;
    private String icon;

    private String week;


    {
        this.province = "上海";
        this.adcode = 310115;
        this.city = "浦东新区";
        this.isnow = "0";
    }
}
