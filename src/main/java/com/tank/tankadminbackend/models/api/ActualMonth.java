package com.tank.tankadminbackend.models.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActualMonth {
    @JsonProperty("month")
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
