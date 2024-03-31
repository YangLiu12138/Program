package com.example.mydisney.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mydisney.service.WxApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherController {

    @Autowired
    private WxApi wxApi;

    @RequestMapping("/getBusiness")
    public JSONObject getBusiness() {
        return wxApi.getBusiness();
    }
}
