package com.example.subway.line.acceptance;

import com.example.subway.common.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.example.subway.line.acceptance.LineStep.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {

    private static Map<String, String> 경춘선;
    private static Map<String, String> 신분당선;

    @BeforeAll
    public static void given() {
        경춘선 = LineFixData.create_경춘선();
        신분당선 = LineFixData.create_신분당선();
    }

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(경춘선);

        // then
        노선_생성_확인(response, 경춘선);
    }

    @DisplayName("기존에 존재하는 지하철 노선 이름으로 지하철 노선을 생성한다.")
    @Test
    void createLine2() {
        // given
        노선_등록되어_있음(경춘선);

        // when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(경춘선);

        // then
        // 지하철_노선_생성_실패됨
        노선_이름_중복_실패_확인(response);
    }


    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void getLines() {
        // given
        // 지하철_노선_등록되어_있음
        // 지하철_노선_등록되어_있음
        노선_등록되어_있음(경춘선);
        노선_등록되어_있음(신분당선);

        // when
        // 지하철_노선_목록_조회_요청
        ExtractableResponse<Response> response = 지하철_노선_목록_조회_요청();

        // then
        // 지하철_노선_목록_응답됨
        // 지하철_노선_목록_포함됨
        노선_목록_확인(response);
    }

    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void getLine() {
        // given
        // 지하철_노선_등록되어_있음
        노선_등록되어_있음(경춘선);

        // when
        // 지하철_노선_조회_요청
        ExtractableResponse<Response> response = 지하철_노선_조회_요청(1L);

        // then
        // 지하철_노선_응답됨
        노선_조회_확인(response, 경춘선);
    }

    @DisplayName("존재하지 않는 지하철 노선을 조회한다.")
    @Test
    void getNotExistedLine() {
        // when
        ExtractableResponse<Response> response = 지하철_노선_조회_요청(1L);

        // then
        // 지하철_노선_응답됨
        노선_없음_확인(response);
    }

    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void updateLine() {
        // given
        // 지하철_노선_등록되어_있음
        노선_등록되어_있음(경춘선);

        // when
        // 지하철_노선_수정_요청
        ExtractableResponse<Response> response = 지하철_노선_수정_요청(1L, 신분당선);

        // then
        노선_변경_확인(response);

        // then
        // 지하철_노선_수정됨
        ExtractableResponse<Response> updatedResponse = 지하철_노선_조회_요청(1L);

        // then
        // 지하철_노선_응답됨
        노선_조회_확인(updatedResponse, 신분당선);
    }

    @DisplayName("존재하지 않는 지하철 노선을 수정한다.")
    @Test
    void updateNotExistedLine() {
        // when
        // 지하철_노선_수정_요청
        ExtractableResponse<Response> response = 지하철_노선_수정_요청(1L, 신분당선);

        // then
        노선_없음_확인(response);
    }

    @DisplayName("지하철 노선을 제거한다.")
    @Test
    void deleteLine() {
        // given
        // 지하철_노선_등록되어_있음
        노선_등록되어_있음(경춘선);

        // when
        // 지하철_노선_제거_요청
        ExtractableResponse<Response> response = 지하철_노선_제거_요청(1L);

        // then
        // 지하철_노선_삭제됨
        노선_변경_확인(response);
    }

    @DisplayName("존재하지 않는 지하철 노선을 제거한다.")
    @Test
    void deleteNotExistedLine() {

        // when
        // 지하철_노선_제거_요청
        ExtractableResponse<Response> response = 지하철_노선_제거_요청(1L);


        // then
        // 지하철_노선_삭제됨
        노선_없음_확인(response);
    }
}