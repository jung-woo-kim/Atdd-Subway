package com.example.subway.member.application.dto;

public class TokenResponse {
    private String accessToken;

    private TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken);
    }
}
