package com.example.mydisney.controller;


import com.example.mydisney.service.IPlaytimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 杨柳
 * @since 2024-01-09
 */
@RestController
@RequestMapping("/playtime")
public class PlaytimeController {

    @Autowired
    private IPlaytimeService playtimeService;

    @GetMapping("/getTime")
    public String getTime() {
        return playtimeService.getTime();
    }
}
