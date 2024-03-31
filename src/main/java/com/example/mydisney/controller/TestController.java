package com.example.mydisney.controller;

import com.example.mydisney.entity.Business;
import com.example.mydisney.mapper.BussinessMapper;
import com.example.mydisney.service.IPlaytimeService;
import com.example.mydisney.service.WeatherApi;
import com.example.mydisney.task.AppiumAppTask;
import com.example.mydisney.task.CarParkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private WeatherApi weatherApi;

    @Autowired
    private CarParkFlow carParkFlow;

    @Autowired
    private AppiumAppTask appiumAppTask;

    @Autowired
    private BussinessMapper bussinessMapper;
    @RequestMapping("/test")
    public String getTest() {
        return "连接成功！";
    }

    @RequestMapping("/weather")
    public String getWeather() throws Exception {
        weatherApi.updateWeather();
        return "11";
    }


    @RequestMapping("/nowWeather")
    public String getnowWeather() {
        weatherApi.updateWeatherNow();
        return "12";
    }
    @RequestMapping("/getPark")
    public String getPark() {
        carParkFlow.getParkTime();
        return "12";
    }

    @RequestMapping("/getPlayTime")
    public void getPlayTime(){
        appiumAppTask.setupPlayTime();
    }

    @RequestMapping("/test1")
    public void testMybatis(){
        Business business = new Business();
        business.setDate("2020-08-02");
        bussinessMapper.insert(business);
    }
}
