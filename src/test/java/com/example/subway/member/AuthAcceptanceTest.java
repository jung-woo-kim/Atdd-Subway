package com.example.subway.member;

import com.example.subway.common.AcceptanceTest;
import com.example.subway.member.mock.GithubResponses;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.subway.member.MemberSteps.깃허브_소셜_로그인_요청;
import static com.example.subway.member.MemberSteps.베어러_인증_로그인_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "admin@email.com";
    private static final String PASSWORD = "password";

    @DisplayName("Bearer Auth")
    @Test
    void bearerAuth() {
        ExtractableResponse<Response> response = 베어러_인증_로그인_요청(EMAIL, PASSWORD);

        assertThat(response.jsonPath().getString("accessToken")).isNotBlank();
    }

    @DisplayName("깃헙 로그인")
    @Test
    void githubAuth() {
        ExtractableResponse<Response> 깃허브_소셜_로그인_응답 = 깃허브_소셜_로그인_요청(GithubResponses.사용자1.getCode());
        assertThat(깃허브_소셜_로그인_응답.jsonPath().getString("accessToken")).isNotBlank();

    }
}
