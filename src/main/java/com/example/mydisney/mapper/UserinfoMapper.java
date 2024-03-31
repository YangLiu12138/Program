package com.example.mydisney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydisney.entity.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserinfoMapper extends BaseMapper<Userinfo> {



}
