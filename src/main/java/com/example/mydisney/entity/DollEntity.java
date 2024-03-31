package com.example.mydisney.entity;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;

@Data
public class DollEntity {

    private Integer id;
    private String icon;
    private String name;
    private String introduce;
    private String belong;

    private String parentId;

    private String remake;

    private BigDecimal price;
}
