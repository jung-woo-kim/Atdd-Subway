package com.example.subway.common;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

public class CommonRestAssured {
    public static ExtractableResponse<Response> get(String path) {
        return RestAssured.given().log().all()
                .when().get(path).
                then().log().all().extract();
    }

    public static ExtractableResponse<Response> get(String path, Long id) {
        return RestAssured.given().log().all()
                .when().get(path + "/" + id).
                then().log().all().extract();
    }

    public static ExtractableResponse<Response> post(String path, Map<String, String> params) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> update(String path, Long id, Map<String, String> params) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path + "/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(String path, Long id) {
        return RestAssured.given().log().all()
                .when().delete(path + "/" + id)
                .then().log().all()
                .extract();
    }
}
