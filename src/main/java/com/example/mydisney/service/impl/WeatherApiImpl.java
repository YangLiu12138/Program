package com.example.mydisney.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mydisney.mapper.WeatherMapper;
import com.example.mydisney.entity.Weather;
import com.example.mydisney.service.WeatherApi;
import com.example.mydisney.tool.DateUtil;
import com.example.mydisney.tool.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeatherApiImpl implements WeatherApi {


    @Autowired
    private WeatherMapper weatherMapper;

    @Autowired
    private DateUtil dateUtil;

    @Value("${weather.key}")
    private String hfKey;


    @Override
    public void updateWeather(){
        String url = "https://devapi.qweather.com/v7/weather/7d?location=101020600&key="+hfKey;
        String weather= HttpUtil.doGet(url, null);
        if (!StringUtils.isEmpty(weather)) {
            JSONObject jsonObject = JSONObject.parseObject(weather);
            if ("200".equals(jsonObject.get("code"))) {
                String currentDate = dateUtil.getCurrentDate(null);
                String currentNextDate = dateUtil.getCurrentDate("-7");
                weatherMapper.deleteByDate(currentDate,currentNextDate);
                List<JSONObject> cityWeatherList = (List<JSONObject>) jsonObject.get("daily");
                for (JSONObject object : cityWeatherList) {
                    Weather weatherEntity = new Weather();
                    weatherEntity.setDate((String) object.get("fxDate"));
                    weatherEntity.setDayweather((String) object.get("textDay"));
                    weatherEntity.setNightweather((String) object.get("textNight"));
                    weatherEntity.setDaytemp((String) object.get("tempMax"));
                    weatherEntity.setNighttemp((String) object.get("tempMin"));
                    weatherEntity.setDaywind((String) object.get("windDirDay"));
                    weatherEntity.setNightwind((String) object.get("windDirNight"));
                    weatherEntity.setDaypower((String) object.get("windScaleDay"));
                    weatherEntity.setNightpower((String) object.get("windScaleNight"));
                    weatherEntity.setIcon((String) object.get("iconDay"));
                    weatherEntity.setWeek(getWeek(weatherEntity.getDate()));
                    weatherMapper.updateWeather(weatherEntity);
                }
            }
        }
    }

    @Override
    public void updateWeatherNow() {
        String currentDate = dateUtil.getCurrentDate(null);
        Weather weatherIsNow = weatherMapper.queryByIsnow(currentDate);
        if (weatherIsNow != null) {
            weatherMapper.DeleteIsnow(currentDate);
        }
        String url = "https://devapi.qweather.com/v7/weather/now?location=101020600&key="+hfKey;
        String weather= HttpUtil.doGet(url, null);
        if (!StringUtils.isEmpty(weather)) {
            JSONObject jsonObject = JSONObject.parseObject(weather);
            if ("200".equals(jsonObject.get("code"))) {
                JSONObject  now = (JSONObject) jsonObject.get("now");
                Weather weatherEntity = new Weather();
                weatherEntity.setIsnow("1");
                weatherEntity.setTemperature((String) now.get("temp"));
                weatherEntity.setIcon((String) now.get("icon"));
                weatherEntity.setWeather((String) now.get("text"));
                weatherEntity.setWinddirection((String) now.get("windDir"));
                weatherEntity.setWindpower((String) now.get("windScale"));
                weatherEntity.setDate(currentDate);
                weatherEntity.setWeek(getWeek(weatherEntity.getDate()));
                weatherMapper.updateWeather(weatherEntity);
            }
        }
    }


    @Override
    public String getWeather() {
        String currentDate = dateUtil.getCurrentDate(null);
        String currentNextDate = dateUtil.getCurrentDate("-7");
        List<Weather> weatherList = weatherMapper.queryByDate(currentDate, currentNextDate);
        if (weatherList != null) {
            return JSON.toJSONString(weatherList);
        }
        return null;
    }

    @Override
    public List<String> getFutureWeather() {
        List<Weather> weatherList = weatherMapper.queryFutureWeather();
        List<String> result = new ArrayList<>();
        for (Weather weather : weatherList) {
            result.add(JSON.toJSONString(weather));
        }
        return result;
    }

    @Override
    public String getWeatherNow() {
        String currentDate = dateUtil.getCurrentDate(null);
        Weather weather = weatherMapper.queryByIsnow(currentDate);
        if (weather != null) {
            return JSON.toJSONString(weather);
        }
        return null;
    }

    public static String getWeek(String sdate) {
        // 再转换为时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Date parseDate = null;
        try {
            parseDate = dateFormat.parse(sdate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate);
        int week_index = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }


}
