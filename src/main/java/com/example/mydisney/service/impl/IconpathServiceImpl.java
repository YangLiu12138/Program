package com.example.mydisney.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.mydisney.entity.Iconpath;
import com.example.mydisney.mapper.IconpathMapper;
import com.example.mydisney.service.IIconpathService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨柳
 * @since 2024-01-10
 */
@Service
public class IconpathServiceImpl extends ServiceImpl<IconpathMapper, Iconpath> implements IIconpathService {

    @Autowired
    private IconpathMapper iconpathMapper;

    @Override
    public String getIndex() {
        List<Iconpath> index = iconpathMapper.getIndex();

        return JSONArray.toJSONString(index);
    }
}
