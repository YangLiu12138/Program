package com.example.mydisney.controller;

import com.example.mydisney.service.CarFlowApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarFlowController {

    @Autowired
    private CarFlowApi carFlowApi;

    @GetMapping("/getCarFlow")
    public String getCarFlow() {
      return  carFlowApi.getCarFlow();
    }
}
