package com.example.subway.line.domain;

import com.example.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubwayMapTest {
    private Station 교대역;
    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;
    private Line 신분당선;
    private Line 이호선;
    private Line 삼호선;

    @BeforeEach
    void setUp() {
        교대역 = new Station(1L, "교대역");
        강남역 = new Station(2L, "강남역");
        양재역 = new Station(3L, "양재역");
        남부터미널역 = new Station(4L, "남부터미널역");

        신분당선 = new Line("신분당선", "red");
        이호선 = new Line("2호선", "red");
        삼호선 = new Line("3호선", "red");

        신분당선.addSection(강남역, 양재역, 3);
        이호선.addSection(교대역, 강남역, 3);
        삼호선.addSection(교대역, 남부터미널역, 5);
        삼호선.addSection(남부터미널역, 양재역, 5);
    }

    @Test
    void findPath() {
    }
}