package com.example.mydisney.service;

import com.example.mydisney.entity.Playtime;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 杨柳
 * @since 2024-01-09
 */
public interface IPlaytimeService extends IService<Playtime> {

    String getTime();
}
