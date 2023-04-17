package com.example.subway.station;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Map;

import static com.example.subway.common.CommonAssert.요청_생성_확인;
import static com.example.subway.common.CommonAssert.요청_조회_확인;
import static com.example.subway.common.CommonRestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StationStep {
    private static final String path = "/stations";

    public static ExtractableResponse<Response> 지하철_역_생성_요청(Map<String, String> params) {
        return post(path, params);
    }

    public static ExtractableResponse<Response> 지하철_역_목록_조회_요청() {
        return get(path);
    }

    public static void 지하철_역_삭제_요청(Long id) {
        delete(path, id);
    }

    public static void 역_생성_확인(ExtractableResponse<Response> response) {
        요청_생성_확인(response);
    }

    public static void 역_존재_확인(ExtractableResponse<Response> response, String name) {
        assertThat(response.jsonPath().getList("name",String.class)).containsAnyOf(name);
    }

    public static void 역_미존재_확인(ExtractableResponse<Response> response, String name) {
        assertFalse(response.jsonPath().getList("name",String.class).contains(name));
    }

    public static void 역_생성_되어있음(Map<String, String> params) {
        지하철_역_생성_요청(params);
    }

    public static void 역_목록_확인(ExtractableResponse<Response> response) {
        요청_조회_확인(response);
        assertEquals(2, response.jsonPath().getList("name", String.class).size());
    }
}
