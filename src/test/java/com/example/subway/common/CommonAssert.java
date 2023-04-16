package com.example.subway.common;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonAssert {

    public static void 요청_없음_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.NOT_FOUND.value(), response.statusCode());
    }

    public static void 요청_변경_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
    }

    public static void 요청_생성_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    public static void 요청_조회_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.OK.value(), response.statusCode());
    }

    public static void 요청_BAD_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
    }
}
