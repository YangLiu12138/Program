package com.example.mydisney.entity;

import lombok.Data;

import java.util.Date;


@Data
public class CarParkFlowEntity {

    private Integer id;

    private String name;

    private String flow;

    private Date time;

    private String date;

}
