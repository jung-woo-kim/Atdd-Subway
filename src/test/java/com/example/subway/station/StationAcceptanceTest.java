package com.example.subway.station;

import com.example.subway.common.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.example.subway.station.StationStep.*;

/**
 * Feature: 지하철 노선 관리 기능
 */
@DisplayName("지하철역 관련 기능")
public class StationAcceptanceTest extends AcceptanceTest {
    private static Map<String, String> 강남역;
    private static Map<String, String> 성수역;

    @BeforeAll
    public static void given() {
        강남역 = StationFixData.create_강남역();
        성수역 = StationFixData.create_성수역();
    }
    /**
     * When 지하철역을 생성하면
     * Then 지하철역이 생성된다
     * Then 지하철역 목록 조회 시 생성한 역을 찾을 수 있다
     */
    @DisplayName("지하철역을 생성한다.")
    @Test
    void createStation() {
        // when
        ExtractableResponse<Response> response = 지하철_역_생성_요청(강남역);

        // then
        역_생성_확인(response);

        // then
        ExtractableResponse<Response> readResponse = 지하철_역_목록_조회_요청();

        역_존재_확인(readResponse,StationFixData.강남역_이름);
    }

    /**
     * Given 2개의 지하철역을 생성하고
     * When 지하철역 목록을 조회하면
     * Then 2개의 지하철역을 응답 받는다
     */
    // TODO: 지하철역 목록 조회 인수 테스트 메서드 생성
    @DisplayName("지하철역을 두개 생성하고, 목록을 조회하면 2개의 지하철역을 응답 받는다.")
    @Test
    void readStation() {
        //given
        역_생성_되어있음(강남역);


        역_생성_되어있음(성수역);
        //when
        ExtractableResponse<Response> response = 지하철_역_목록_조회_요청();

        //then
        역_목록_확인(response);

    }

    /**
     * Given 지하철역을 생성하고
     * When 그 지하철역을 삭제하면
     * Then 그 지하철역 목록 조회 시 생성한 역을 찾을 수 없다
     */
    // TODO: 지하철역 제거 인수 테스트 메서드 생성
    @DisplayName("지하철역을 삭제한다.")
    @Test
    void deleteStation() {
        //given
        역_생성_되어있음(강남역);

        //when
        지하철_역_삭제_요청(1L);


        ExtractableResponse<Response> response = 지하철_역_목록_조회_요청();

        //then
        역_미존재_확인(response, StationFixData.강남역_이름);
    }

}
