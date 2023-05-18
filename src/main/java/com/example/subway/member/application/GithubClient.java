package com.example.subway.member.application;

public interface GithubClient {
    String getAccessTokenFromGithub(String code);
    String getGithubProfileFromGithub(String accessToken);
}
