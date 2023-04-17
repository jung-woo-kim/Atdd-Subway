package com.example.subway.line.acceptance;

import com.example.subway.line.exception.LineExceptionType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Map;

import static com.example.subway.common.CommonAssert.*;
import static com.example.subway.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineStep {
    private static final String PATH = "/lines";
    private static final String NAME = "name";
    private static final String COLOR = "color";
    private static final String ERROR_CODE = "errorCode";



    public static ExtractableResponse<Response> 지하철_노선_생성_요청(Map<String, String> params) {
        return post(PATH, params);
    }

    public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청() {
        return get(PATH);
    }

    public static ExtractableResponse<Response> 지하철_노선_조회_요청(Long id) {
        return get(PATH, id);
    }

    public static ExtractableResponse<Response> 지하철_노선_수정_요청(Long id, Map<String, String> params ) {
        return update(PATH, id, params);
    }

    public static ExtractableResponse<Response> 지하철_노선_제거_요청(Long id) {
        return delete(PATH, id);
    }

    public static void 노선_생성_확인(ExtractableResponse<Response> response, Map<String, String> params) {
        요청_생성_확인(response);
        노선_생성_값_확인(response, params);
    }

    public static void 노선_생성_값_확인(ExtractableResponse<Response> response, Map<String, String> params) {
        assertEquals(params.get(NAME), response.jsonPath().get(NAME).toString());
        assertEquals(params.get(COLOR), response.jsonPath().get(COLOR).toString());
    }

    public static void 노선_목록_확인(ExtractableResponse<Response> response) {
        요청_조회_확인(response);
        assertEquals(2, response.jsonPath().getList(NAME, String.class).size());
    }

    public static void 노선_조회_확인(ExtractableResponse<Response> response, Map<String, String> params) {
        요청_조회_확인(response);
        노선_생성_값_확인(response, params);
    }

    public static void 노선_이름_중복_실패_확인(ExtractableResponse<Response> response) {
        요청_BAD_확인(response);
        assertEquals(LineExceptionType.LINE_DUPLICATE.getErrorCode(), (Integer) response.jsonPath().get(ERROR_CODE));
    }

    public static void 노선_없음_확인(ExtractableResponse<Response> response) {
        요청_없음_확인(response);
    }

    public static void 노선_변경_확인(ExtractableResponse<Response> response) {
        요청_변경_확인(response);
    }

    public static void 노선_등록되어_있음(Map<String, String> params) {
        지하철_노선_생성_요청(params);
    }
}
