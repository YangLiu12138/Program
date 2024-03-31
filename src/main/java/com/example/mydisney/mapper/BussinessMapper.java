package com.example.mydisney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydisney.entity.Business;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BussinessMapper extends BaseMapper<Business> {

    public void saveBussiness(Business business);

    public Business queryByCurrTime(String currTime);

}
