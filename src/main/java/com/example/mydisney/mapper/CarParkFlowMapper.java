package com.example.mydisney.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydisney.entity.CarParkFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CarParkFlowMapper extends BaseMapper<CarParkFlowEntity> {

    int insert(CarParkFlowEntity carParkFlowEntity);

    void update(CarParkFlowEntity carParkFlowEntity);

    int count(CarParkFlowEntity carParkFlowEntity);


    List<CarParkFlowEntity> queryByDate(String date);
}
