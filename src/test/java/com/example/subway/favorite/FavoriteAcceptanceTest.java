package com.example.subway.favorite;

import com.example.subway.common.AcceptanceTest;
import com.example.subway.line.acceptance.LineFixData;
import com.example.subway.station.StationFixData;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.example.subway.favorite.FavoriteSteps.*;
import static com.example.subway.line.acceptance.LineStep.지하철_노선_생성_요청;
import static com.example.subway.line.acceptance.SectionStep.지하철_노선에_지하철_구간_생성_요청;
import static com.example.subway.member.MemberSteps.베어러_인증_로그인_요청;
import static com.example.subway.member.MemberSteps.회원_생성_요청;
import static com.example.subway.station.StationStep.지하철_역_생성_요청;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavoriteAcceptanceTest extends AcceptanceTest {

    private Long 교대역;
    private Long 강남역;
    private Long 양재역;
    private Long 남부터미널역;
    private Long 이호선;
    private Long 신분당선;
    private Long 삼호선;

    /**
     * 교대역    --- *2호선* ---   강남역
     * |                        |
     * *3호선*                   *신분당선*
     * |                        |
     * 남부터미널역  --- *3호선* ---   양재
     */
    @BeforeEach
    public void setUp() {
        super.setUp();

        교대역 = 지하철_역_생성_요청(StationFixData.createStationParams("교대역")).jsonPath().getLong("id");
        강남역 = 지하철_역_생성_요청(StationFixData.createStationParams("강남역")).jsonPath().getLong("id");
        양재역 = 지하철_역_생성_요청(StationFixData.createStationParams("양재역")).jsonPath().getLong("id");
        남부터미널역 = 지하철_역_생성_요청(StationFixData.createStationParams("남부터미널역")).jsonPath().getLong("id");

        이호선 = 지하철_노선_생성_요청(LineFixData.createLineCreateParams("2호선", "green", 교대역, 강남역, 10)).jsonPath().getLong("id");
        신분당선 = 지하철_노선_생성_요청(LineFixData.createLineCreateParams("신분당선", "red", 강남역, 양재역, 10)).jsonPath().getLong("id");
        삼호선 = 지하철_노선_생성_요청(LineFixData.createLineCreateParams("3호선", "orange", 교대역, 남부터미널역, 2)).jsonPath().getLong("id");

        지하철_노선에_지하철_구간_생성_요청(삼호선, createSectionCreateParams(남부터미널역, 양재역, 3));
    }

    @DisplayName("즐겨찾기 생성, 중복 생성, 조회, 삭제")
    @Test
    void favoriteCRUD() {
        // given
        회원_생성_요청("temp@email.com", "temp",20);
        String accessToken = 베어러_인증_로그인_요청("temp@email.com", "temp").jsonPath().getString("accessToken");


        // when
        // 교대역 남부미널역 양재역
        ExtractableResponse<Response> 즐겨찾기_생성_응답 = 즐겨찾기_생성_요청(accessToken,createFavoriteCreateParams(교대역, 양재역));

        // then
        assertEquals(HttpStatus.CREATED.value(), 즐겨찾기_생성_응답.statusCode());

        // when
        ExtractableResponse<Response> 즐겨찾기_중복_생성_응답 = 즐겨찾기_생성_요청(accessToken,createFavoriteCreateParams(교대역, 양재역));

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), 즐겨찾기_중복_생성_응답.statusCode());

        // when
        ExtractableResponse<Response> 즐겨찾기_조회_응답 = 즐겨찾기_조회_요청(accessToken);

        // then
        assertEquals(HttpStatus.OK.value(), 즐겨찾기_조회_응답.statusCode());
        assertEquals(1, 즐겨찾기_조회_응답.jsonPath().getList("id").size());
    }

    private Map<String, String> createSectionCreateParams(Long upStationId, Long downStationId, int distance) {
        Map<String, String> params = new HashMap<>();
        params.put("upStationId", upStationId + "");
        params.put("downStationId", downStationId + "");
        params.put("distance", distance + "");
        return params;
    }

    private Map<String, String> createFavoriteCreateParams(Long upStationId, Long downStationId) {
        Map<String, String> params = new HashMap<>();
        params.put("source", upStationId + "");
        params.put("target", downStationId + "");
        return params;
    }
}
