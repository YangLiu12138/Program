package com.example.mydisney.mapper;

import com.example.mydisney.entity.Playtime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨柳
 * @since 2024-01-09
 */
@Mapper
public interface PlaytimeMapper extends BaseMapper<Playtime> {

    List<Map<String, String>> getTime(String currDate);
}
