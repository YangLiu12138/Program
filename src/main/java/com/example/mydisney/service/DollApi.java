package com.example.mydisney.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface DollApi {
    List<Map<String,String>> getDollPath(String id);

    List<Map<String, String>> getDollByType(String id);
}
