package com.example.mydisney.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface WeatherApi {

    public void updateWeather() throws Exception;

    public void updateWeatherNow();

    public String getWeather();

    List<String> getFutureWeather();

    String getWeatherNow();
}
