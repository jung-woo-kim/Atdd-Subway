package com.example.subway.favorite;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavoriteSteps {
    public static ExtractableResponse<Response> 즐겨찾기_생성_요청(String accessToken, Map<String, String> params) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/favorites")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 즐겨찾기_조회_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/favorites")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 즐겨찾기_삭제_요청(String accessToken, String location) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(location)
                .then().log().all().extract();
    }

    public static void 즐겨찾기_삭제_확인(String accessToken, ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.NO_CONTENT.value(),response.statusCode());

        ExtractableResponse<Response> 즐겨찾기_조회_응답 = 즐겨찾기_조회_요청(accessToken);
        assertEquals(HttpStatus.OK.value(), 즐겨찾기_조회_응답.statusCode());
        assertEquals(0, 즐겨찾기_조회_응답.jsonPath().getList("id").size());
    }
}
