package com.tank.tankadminbackend.payload.request;

import javax.validation.constraints.NotBlank;

public class LogOutRequest {
    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
