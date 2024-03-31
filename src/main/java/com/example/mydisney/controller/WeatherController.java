package com.example.mydisney.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.mydisney.service.WeatherApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class WeatherController {

    @Autowired
    private WeatherApi weatherApi;

    @RequestMapping("/getWeather")
    public String getWeather() {
        return weatherApi.getWeather();
    }


    @RequestMapping("/getFutureWeather")
    public List<String> getFutureWeather() {
        return weatherApi.getFutureWeather();
    }


    @RequestMapping("/getWeatherNow")
    public String getWeatherNow() {
        return weatherApi.getWeatherNow();
    }
}
