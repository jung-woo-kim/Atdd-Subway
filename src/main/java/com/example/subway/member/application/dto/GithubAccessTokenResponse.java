package com.example.subway.member.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    public GithubAccessTokenResponse() {
    }

    public GithubAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
