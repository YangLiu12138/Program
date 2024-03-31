package com.example.mydisney.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mydisney.service.DollApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("/doll")
public class DollController {

    @Autowired
    private DollApi dollApi;

    @GetMapping("/getDollPath")
    public List<Map<String,String>> getDollPath(String id) {
        return dollApi.getDollPath(id);
    }


    @GetMapping("/getDollByType")
    public List<Map<String,String>> getDollByType(String id) {
        return dollApi.getDollByType(id);
    }
}
