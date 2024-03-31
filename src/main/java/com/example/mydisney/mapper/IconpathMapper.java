package com.example.mydisney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydisney.entity.Iconpath;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IconpathMapper extends BaseMapper<Iconpath> {

    List<Iconpath> getIndex();
}
