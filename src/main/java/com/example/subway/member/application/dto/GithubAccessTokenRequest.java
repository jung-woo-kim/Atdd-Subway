package com.example.subway.member.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubAccessTokenRequest {
    private String code;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;

    public GithubAccessTokenRequest() {
    }

    private GithubAccessTokenRequest(String code, String clientId, String clientSecret) {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getCode() {
        return code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public static GithubAccessTokenRequest createGithubAccessTokenRequest(String code, String clientId, String clientSecret) {
        return new GithubAccessTokenRequest(code, clientId, clientSecret);
    }
}
