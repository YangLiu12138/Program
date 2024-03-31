package com.example.mydisney.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.mydisney.entity.Playtime;
import com.example.mydisney.mapper.PlaytimeMapper;
import com.example.mydisney.service.IPlaytimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mydisney.tool.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨柳
 * @since 2024-01-09
 */
@Service
public class PlaytimeServiceImpl extends ServiceImpl<PlaytimeMapper, Playtime> implements IPlaytimeService {

    @Autowired
    private PlaytimeMapper playtimeMapper;

    @Autowired
    private DateUtil dateUtil;
    @Override
    public String getTime() {
        List<Map<String, String>> times = playtimeMapper.getTime(dateUtil.getCurrentDate(null));
        return JSONArray.toJSONString(times);
    }
}
