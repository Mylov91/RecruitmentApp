package ru.mylov.Recruitment.dto;

import org.springframework.stereotype.Component;

@Component
public class RefreshTokenRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
