package com.example.mydisney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydisney.entity.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WeatherMapper extends BaseMapper<Weather> {


    public List<Weather> queryByDate(String beginDate,String endDate);

    public void updateWeather(Weather weather);

    public Weather queryByIsnow(String beginDate);

    public void DeleteIsnow(String beginDate);


    List<Weather> queryFutureWeather();

    void deleteByDate(String beginDate, String endDate);
}
