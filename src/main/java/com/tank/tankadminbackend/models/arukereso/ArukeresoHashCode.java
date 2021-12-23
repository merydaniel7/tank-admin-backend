package com.tank.tankadminbackend.models.arukereso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArukeresoHashCode {
    @JsonProperty("Result")
    private String arukeresoHashCode;

    public String getArukeresoHashCode() {
        return arukeresoHashCode;
    }

    public void setArukeresoHashCode(String arukeresoHashCode) {
        this.arukeresoHashCode = arukeresoHashCode;
    }
}
