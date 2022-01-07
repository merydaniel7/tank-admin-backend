package com.tank.tankadminbackend.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateHelper {

    public String getYesterdayString() {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);
        return yesterday.toString();
    }
}
