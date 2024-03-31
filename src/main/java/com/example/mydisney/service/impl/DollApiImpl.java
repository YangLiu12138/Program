package com.example.mydisney.service.impl;

import com.example.mydisney.mapper.DollMapper;
import com.example.mydisney.entity.DollEntity;
import com.example.mydisney.service.DollApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DollApiImpl implements DollApi {

    @Autowired
    private DollMapper dollMapper;

    @Override
    public List<Map<String,String>> getDollPath(String id) {
        List<DollEntity> result = dollMapper.getDollPath(id);
        List<Map<String,String>> resultList = new ArrayList<>();
        for (DollEntity dollEntity : result) {
            Map<String,String> map = new HashMap<>();
            map.put("id", String.valueOf(dollEntity.getId()));
            map.put("type", dollEntity.getBelong());
            map.put("src",dollEntity.getIcon());
            map.put("name",dollEntity.getName());
            map.put("introduce",dollEntity.getIntroduce());
            map.put("remake",dollEntity.getRemake());
            map.put("price",String.valueOf(dollEntity.getPrice()));
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<Map<String, String>> getDollByType(String id) {
        List<DollEntity> result = dollMapper.getDollByType(id);
        List<Map<String,String>> resultList = new ArrayList<>();
        for (DollEntity dollEntity : result) {
            Map<String,String> map = new HashMap<>();
            map.put("id", String.valueOf(dollEntity.getId()));
            map.put("type", dollEntity.getBelong());
            map.put("src",dollEntity.getIcon());
            map.put("name",dollEntity.getName());
            map.put("introduce",dollEntity.getIntroduce());
            map.put("remake",dollEntity.getRemake());
            map.put("price",String.valueOf(dollEntity.getPrice()));
            resultList.add(map);
        }
        return resultList;
    }
}
