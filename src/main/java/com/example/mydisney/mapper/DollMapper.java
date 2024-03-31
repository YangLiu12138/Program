package com.example.mydisney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydisney.entity.DollEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨某人
 * @since 2024-01-05
 */
@Mapper
public interface DollMapper extends BaseMapper<DollEntity> {

    List<DollEntity> getDollPath(String id);

    List<DollEntity> getDollByType(String id);
}
