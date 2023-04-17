package com.example.subway.line.acceptance;

import com.example.subway.common.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.example.subway.line.acceptance.LineStep.지하철_노선_생성_요청;
import static com.example.subway.line.acceptance.LineStep.지하철_노선_조회_요청;
import static com.example.subway.line.acceptance.SectionStep.지하철_노선에_지하철_구간_생성_요청;
import static com.example.subway.station.StationFixData.*;
import static com.example.subway.station.StationStep.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 구간 관리 기능")
public class SectionAcceptanceTest extends AcceptanceTest {

    private Long 신분당선;

    private Long 강남역;
    private Long 성수역;

    /**
     * Given 지하철역과 노선 생성을 요청 하고
     */
    @BeforeEach
    public void setUp() {
        super.setUp();

        강남역 = 지하철_역_생성_요청(create_강남역_params()).jsonPath().getLong("id");
        성수역 = 지하철_역_생성_요청(create_성수역_params()).jsonPath().getLong("id");

        Map<String, String> lineCreateParams = createLineCreateParams(강남역, 성수역);
        신분당선 = 지하철_노선_생성_요청(lineCreateParams).jsonPath().getLong("id");
    }

    /**
     * When 지하철 노선에 새로운 구간 추가를 요청 하면
     * Then 노선에 새로운 구간이 추가된다
     */
    @DisplayName("지하철 노선에 구간을 등록")
    @Test
    void addLineSection() {
        // when
        Long 정자역 = 지하철_역_생성_요청(create_정자역_params()).jsonPath().getLong("id");
        지하철_노선에_지하철_구간_생성_요청(신분당선, createSectionCreateParams(성수역, 정자역));

        // then
        ExtractableResponse<Response> response = 지하철_노선_조회_요청(신분당선);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("stations.id", Long.class)).containsExactly(강남역, 성수역, 정자역);
    }

    /**
     * Given 지하철 노선에 새로운 구간 추가를 요청 하고
     * When 지하철 노선의 마지막 구간 제거를 요청 하면
     * Then 노선에 구간이 제거된다
     */
//    @DisplayName("지하철 노선에 구간을 제거")
//    @Test
//    void removeLineSection() {
//        // given
//        Long 정자역 = 지하철_역_생성_요청(create_정자역()).jsonPath().getLong("id");
//        지하철_노선에_지하철_구간_생성_요청(신분당선, createSectionCreateParams(성수역, 정자역));
//
//        // when
//        지하철_노선에_지하철_구간_제거_요청(신분당선, 정자역);
//
//        // then
//        ExtractableResponse<Response> response = 지하철_노선_조회_요청(신분당선);
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.jsonPath().getList("stations.id", Long.class)).containsExactly(강남역, 성수역);
//    }

    private Map<String, String> createLineCreateParams(Long upStationId, Long downStationId) {
        Map<String, String> lineCreateParams;
        lineCreateParams = new HashMap<>();
        lineCreateParams.put("name", "신분당선");
        lineCreateParams.put("color", "bg-red-600");
        lineCreateParams.put("upStationId", upStationId.toString());
        lineCreateParams.put("downStationId", downStationId.toString());
        lineCreateParams.put("distance", 10 + "");
        return lineCreateParams;
    }

    private Map<String, String> createSectionCreateParams(Long upStationId, Long downStationId) {
        Map<String, String> params = new HashMap<>();
        params.put("upStationId", upStationId.toString());
        params.put("downStationId", downStationId.toString());
        params.put("distance", 6 + "");
        return params;
    }
}
