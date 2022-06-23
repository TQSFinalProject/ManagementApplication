package com.tqs.trackit.dtos;

public class AuthTokenDTO {
    private String token;

    public AuthTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
