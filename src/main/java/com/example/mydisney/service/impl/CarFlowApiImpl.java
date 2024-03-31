package com.example.mydisney.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.mydisney.mapper.CarParkFlowMapper;
import com.example.mydisney.entity.CarParkFlowEntity;
import com.example.mydisney.service.CarFlowApi;
import com.example.mydisney.tool.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarFlowApiImpl implements CarFlowApi {

    @Autowired
    private CarParkFlowMapper carParkFlowMapper;
    @Autowired
    private DateUtil dateUtil;

    @Override
    public String getCarFlow() {
        String currentDate = dateUtil.getCurrentDate(null);
        List<CarParkFlowEntity> carParkFlowEntities = carParkFlowMapper.queryByDate(currentDate);
        if (carParkFlowEntities != null) {
            return JSON.toJSONString(carParkFlowEntities);
        }
        return null;
    }
}
