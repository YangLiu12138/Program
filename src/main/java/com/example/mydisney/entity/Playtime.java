package com.example.mydisney.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨柳
 * @since 2024-01-09
 */
@Data
public class Playtime implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String waitTime;

    private LocalDateTime updateTime;

    private String date;


    @Override
    public String toString() {
        return "Playtime{" +
            "id=" + id +
            ", name=" + name +
            ", waitTime=" + waitTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
