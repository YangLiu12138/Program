package com.example.mydisney.tool;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
public class DateUtil {
    public String getCurrentDate(String countDay) {
        // get the current date
        LocalDate currentDate = LocalDate.now();

        if (!StringUtils.isEmpty(countDay)) {
            currentDate = LocalDate.now().minusDays(Long.parseLong(countDay));
        }

        // get the year
        int year = currentDate.getYear();

        // get the month
        int month = currentDate.getMonthValue();

        // get the day
        int day = currentDate.getDayOfMonth();

        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
    }
}