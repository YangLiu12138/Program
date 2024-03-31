package com.example.mydisney.task;

import com.example.mydisney.service.WeatherApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class WeatherTask {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WeatherApi weatherApi;

    @Scheduled(cron = "0 0 0 * * ?")
    void getWeatherByEveryDay() {
        logger.info(LocalDateTime.now() + "开始获取未来天气");
        try {
            weatherApi.updateWeather();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info(LocalDateTime.now() + "开始获取未来天气结束");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    void getNowWeatherByEveryDay() {
        logger.info(LocalDateTime.now() + "开始获取未来天气");
        try {
            weatherApi.getWeatherNow();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info(LocalDateTime.now() + "开始获取未来天气结束");
    }
}
