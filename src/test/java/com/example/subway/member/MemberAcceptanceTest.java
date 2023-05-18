package com.example.subway.member;

import com.example.subway.common.AcceptanceTest;
import com.example.subway.member.mock.GithubResponses;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.example.subway.member.MemberSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    @DisplayName("회원 정보를 관리한다.")
    @Test
    void manageMember() {
        // when
        ExtractableResponse<Response> 회원_생성_응답 = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // then
        assertThat(회원_생성_응답.statusCode()).isEqualTo(HttpStatus.CREATED.value());


        // when
        ExtractableResponse<Response> 회원_정보_조회_응답 = 회원_정보_조회_요청(회원_생성_응답);

        // then
        회원_정보_조회됨(회원_정보_조회_응답, EMAIL, AGE);


        // when
        ExtractableResponse<Response> 회원_정보_수정_응답 = 회원_정보_수정_요청(회원_생성_응답, "new" + EMAIL, "new" + PASSWORD, AGE);

        // then
        assertThat(회원_정보_수정_응답.statusCode()).isEqualTo(HttpStatus.OK.value());

        // when
        ExtractableResponse<Response> 회원_삭제_응답 = 회원_삭제_요청(회원_생성_응답);

        // then
        assertThat(회원_삭제_응답.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("토근을 이용해 정보를 조회한다.")
    @Test
    void getMyInfo() {
        회원_생성_요청(EMAIL, PASSWORD, AGE);
        String accessToken = 베어러_인증_로그인_요청(EMAIL, PASSWORD).jsonPath().getString("accessToken");
        ExtractableResponse<Response> 베어러_인증으로_내_회원_정보_조회_응답 = 베어러_인증으로_내_회원_정보_조회_요청(accessToken);
        회원_정보_조회됨(베어러_인증으로_내_회원_정보_조회_응답, EMAIL, AGE);

    }

    @DisplayName("깃헙 로그인 토큰을 이용해 정보를 조회한다.")
    @Test
    void getMyInfoWithGithub() {
        String accessToken = 깃허브_소셜_로그인_요청(GithubResponses.사용자1.getCode()).jsonPath().getString("accessToken");
        ExtractableResponse<Response> 베어러_인증으로_내_회원_정보_조회_응답 = 베어러_인증으로_내_회원_정보_조회_요청(accessToken);
        회원_정보_조회됨(베어러_인증으로_내_회원_정보_조회_응답, GithubResponses.사용자1.getEmail(), 20);
    }
}
