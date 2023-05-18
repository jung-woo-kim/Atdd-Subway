package com.example.subway.member.mock;

import com.example.subway.member.application.GithubClient;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Primary
@Component
public class MockGithubClient implements GithubClient {
    @Override
    public String getAccessTokenFromGithub(String code) {
        return GithubResponses.getAccessTokenFromCode(code);
    }

    @Override
    public String getGithubProfileFromGithub(String accessToken) {
        return GithubResponses.getEmailFromAccessToken(accessToken);
    }
}
