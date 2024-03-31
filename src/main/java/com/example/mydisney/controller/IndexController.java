package com.example.mydisney.controller;


import com.example.mydisney.service.IIconpathService;
import com.example.mydisney.service.WxApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private IIconpathService iIconpathService;

    @GetMapping("/getIndex")
    public String getIndex() {
        return iIconpathService.getIndex();
    }
}
