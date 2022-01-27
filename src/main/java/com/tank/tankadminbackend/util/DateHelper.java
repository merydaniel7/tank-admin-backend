package com.tank.tankadminbackend.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateHelper {

    public String getYesterdayString() {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);
        return yesterday.toString();
    }

    public List<String> getDatesOfDays(int minusDaysStart, int minusDaysEnd) {
        List<String> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = minusDaysStart; i < minusDaysEnd + 1; i++) {
            dates.add(currentDate.minusDays(i).format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        }

        return dates;
    }

    public List<String> getDatesOfDaysReverse(int minusDaysStart, int minusDaysEnd) {
        List<String> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = minusDaysEnd; i > minusDaysStart - 1; i--) {
            dates.add(currentDate.minusDays(i).format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        }

        return dates;
    }

    public String convertDateToLineFormat(String date) {
        return String.join("-", date.split("\\."));
    }

    public String getNextMonth() {
        return LocalDate.now()
                .with(TemporalAdjusters.firstDayOfNextMonth())
                .format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

}
