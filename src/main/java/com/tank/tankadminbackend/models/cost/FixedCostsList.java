package com.tank.tankadminbackend.models.cost;

import com.fasterxml.jackson.annotation.JsonProperty;


public class FixedCostsList {
    @JsonProperty("fixedCosts")
    private FixedCosts fixedCosts;

    public FixedCosts getFixedCosts() {
        return fixedCosts;
    }

    public void setFixedCosts(FixedCosts fixedCosts) {
        this.fixedCosts = fixedCosts;
    }
}
