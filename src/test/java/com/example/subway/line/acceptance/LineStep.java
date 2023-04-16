package com.example.subway.line.acceptance;

import com.example.subway.line.exception.LineExceptionType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.example.subway.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineStep {
    private static String path = "/lines";

    public static ExtractableResponse<Response> 지하철_노선_생성_요청(String name, String color) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("color", color);

        return post(path, params);
    }

    public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청() {
        return get(path);
    }

    public static ExtractableResponse<Response> 지하철_노선_조회_요청(Long id) {
        return get(path, id);
    }

    public static ExtractableResponse<Response> 지하철_노선_수정_요청(Long id, String name, String color) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("color", color);
        return update(path, id, params);
    }

    public static ExtractableResponse<Response> 지하철_노선_제거_요청(Long id) {
        return delete(path, id);
    }

    public static void 노선_생성_확인(ExtractableResponse<Response> response, String name, String color) {
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        노선_생성_값_확인(response, name, color);
    }

    public static void 노선_생성_값_확인(ExtractableResponse<Response> response, String name, String color) {
        assertEquals(name, response.jsonPath().get("name").toString());
        assertEquals(color, response.jsonPath().get("color").toString());
    }

    public static void 노선_목록_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals(2, response.jsonPath().getList("name", String.class).size());
    }

    public static void 노선_조회_확인(ExtractableResponse<Response> response, String name, String color) {
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        노선_생성_값_확인(response, name, color);
    }

    public static void 노선_이름_중복_실패_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals(LineExceptionType.LINE_DUPLICATE.getErrorCode(), (Integer) response.jsonPath().get("errorCode"));
    }

    public static void 노선_없음_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.NOT_FOUND.value(), response.statusCode());
    }

    public static void 노선_변경_확인(ExtractableResponse<Response> response) {
        assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
    }

    public static void 노선_등록되어_있음(String name, String color) {
        지하철_노선_생성_요청(name, color);
    }
}
