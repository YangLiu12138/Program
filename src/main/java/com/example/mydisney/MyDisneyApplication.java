package com.example.mydisney;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.mydisney.mapper")
@EnableScheduling
public class MyDisneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyDisneyApplication.class, args);
    }

}
