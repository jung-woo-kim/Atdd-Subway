package com.example.subway.line;

import com.example.subway.AcceptanceTest;
import com.example.subway.line.exception.LineExceptionType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        ExtractableResponse<Response> response = createLine("강남역", "bg-red-600");

        // then
        assertEquals(HttpStatus.CREATED.value(),response.statusCode());
        assertEquals("강남역",response.jsonPath().get("name").toString());
        assertEquals("bg-red-600",response.jsonPath().get("color").toString());
    }

    @DisplayName("기존에 존재하는 지하철 노선 이름으로 지하철 노선을 생성한다.")
    @Test
    void createLine2() {
        // given
        createLine("강남역","bg-red-600");

        // when
        ExtractableResponse<Response> response = createLine("강남역","bg-red-600");

        // then
        // 지하철_노선_생성_실패됨
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.statusCode());
        assertEquals(LineExceptionType.LINE_DUPLICATE.getErrorCode(), (Integer) response.jsonPath().get("errorCode"));
    }



    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void getLines() {
        // given
        // 지하철_노선_등록되어_있음
        // 지하철_노선_등록되어_있음
        createLine("강남역","bg-red-600");
        createLine("건국대입구역","bg-red-600");

        // when
        // 지하철_노선_목록_조회_요청
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/lines").
                then().log().all().extract();

        // then
        // 지하철_노선_목록_응답됨
        // 지하철_노선_목록_포함됨
        assertEquals(HttpStatus.OK.value(),response.statusCode());
        assertEquals(2,response.jsonPath().getList("name", String.class).size());
    }

    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void getLine() {
        // given
        // 지하철_노선_등록되어_있음
        createLine("강남역","bg-red-600");

        // when
        // 지하철_노선_조회_요청
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/lines/1").
                then().log().all().extract();
        // then
        // 지하철_노선_응답됨
        assertEquals(HttpStatus.OK.value(),response.statusCode());
        assertEquals("강남역",response.jsonPath().getString("name"));
    }

    @DisplayName("존재하지 않는 지하철 노선을 조회한다.")
    @Test
    void getNotExistedLine() {
        // when
        // 지하철_노선_조회_요청
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/lines/1").
                then().log().all().extract();
        // then
        // 지하철_노선_응답됨
        assertEquals(HttpStatus.NOT_FOUND.value(),response.statusCode());
    }

    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void updateLine() {
        // given
        // 지하철_노선_등록되어_있음

        // when
        // 지하철_노선_수정_요청

        // then
        // 지하철_노선_수정됨
    }

    @DisplayName("지하철 노선을 제거한다.")
    @Test
    void deleteLine() {
        // given
        // 지하철_노선_등록되어_있음

        // when
        // 지하철_노선_제거_요청

        // then
        // 지하철_노선_삭제됨
    }

    private ExtractableResponse<Response> createLine(String name, String color) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("color",color);

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/lines")
                .then().log().all()
                .extract();
    }
}