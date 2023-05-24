package com.example.subway.documentation;

import com.example.subway.line.application.PathService;
import com.example.subway.line.dto.PathResponse;
import com.example.subway.station.dto.StationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

public class PathDocumentation extends Documentation {

    @MockBean
    private PathService pathService;

    @Test
    void findPath() {
        final PathResponse pathResponse = new PathResponse(
                Lists.newArrayList(
                        new StationResponse(1L, "강남역"),
                        new StationResponse(2L, "역삼역")
                ), 10);
        when(pathService.findPath(anyLong(), anyLong())).thenReturn(pathResponse);


        final RequestSpecification requestSpec = documentRequestSpecification()
                .filter(document("path",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("source").description("source id").optional(),
                                parameterWithName("target").description("target id").optional()
                                ),
                        responseFields(
                                fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("역 ID"),
                                fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("역 이름"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("경로 거리")
//                                fieldWithPath("fare").type(JsonFieldType.STRING).description("최단 거리 경로 기준 요금 (원)")
                        )));

        final ExtractableResponse<Response> response = requestSpec
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/paths?source={source}&target={target}", 1L, 2L)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
