package com.example.subway.line.domain;

import com.example.subway.line.acceptance.SectionFixData;
import com.example.subway.line.exception.section.SectionDuplicateException;
import com.example.subway.line.exception.section.SectionMinimumSizeException;
import com.example.subway.line.exception.section.SectionNotLastStationException;
import com.example.subway.station.StationFixData;
import com.example.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.subway.station.StationFixData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SectionsTest {

    private Sections sections;

    @BeforeEach
    void setUp() {
        sections = new Sections();
        sections.addSection(SectionFixData.강남_성수());
    }

    @Test
    void 가장_하행선_등록() {
        sections.addSection(SectionFixData.성수_정자());
        assertEquals(sections.getStations().get(sections.getStations().size()-1), create_정자역());
    }

    @Test
    void 가장_하행선_등록이_아님() {
        assertThrows(SectionNotLastStationException.class,()->sections.addSection(SectionFixData.강남_왕십리()));
    }

    @Test
    void 하행선_이미_등록() {
        assertThrows(SectionDuplicateException.class,() ->sections.addSection(SectionFixData.성수_강남()));
    }

    @Test
    void 역_최소_삭제_실패() {
        assertThrows(SectionMinimumSizeException.class,() -> sections.deleteStation(create_성수역()));
    }

    @Test
    void 역_삭제() {
        sections.addSection(SectionFixData.성수_정자());
        sections.deleteStation(create_정자역());
        assertEquals(sections.getStations().get(sections.getStations().size()-1), create_성수역());
    }

    @Test
    void 역_가장_하행선_아님_실패() {
        assertThrows(SectionNotLastStationException.class,() -> sections.deleteStation(create_강남역()));
    }

    @Test
    void 노선_순서대로_조회() {
        sections.addSection(SectionFixData.성수_정자());
        assertThat(sections.getStations().stream().map(Station::getName)).containsExactly(강남역_이름, 성수역_이름, 정자역_이름);

    }

//    @Test
//    void 상행선_이미_등록() {
//        assertThrows(SectionNotExistedException.class, () -> sections.addSection(Section.createSection(create_강남역(), create_정자역(), LineFixData.createLine_경춘선(), 0)));
//    }
//
//    @Test
//    void 하행선_이미_등록() {
//        assertThrows(SectionNotExistedException.class, () -> sections.addSection(Section.createSection(create_정자역(), create_성수역(), LineFixData.createLine_경춘선(), 0)));
//    }
//    @Test
//    void 가장_상행선_등록() {
//        sections.addSection(create_정자역(), create_강남역(), LineFixData.createLine_경춘선(), 0);
//        assertTrue(sections.getSections().contains(Section.createSection(create_정자역(), create_강남역(), LineFixData.createLine_경춘선(), 0)));
//    }
//
//    @Test
//    void 중간_상행선_등록() {
//        // 강남 성수 정자
//        sections.addSection(create_성수역(), create_정자역(), LineFixData.createLine_경춘선(), 0);
//        // 강남 왕십리 성수 정자
//        sections.addSection(create_왕십리역(), create_성수역(), LineFixData.createLine_경춘선(), 0);
//
//        assertTrue(sections.getSections().contains(Section.createSection(create_왕십리역(), create_성수역(), LineFixData.createLine_경춘선(), 0)));
//        assertTrue(sections.getSections().contains(Section.createSection(create_강남역(), create_왕십리역(), LineFixData.createLine_경춘선(), 0)));
//    }
//
//    @Test
//    void 중간_하행선_등록() {
//        // 강남 성수 정자
//        sections.addSection(create_성수역(), create_정자역(), LineFixData.createLine_경춘선(), 0);
//        // 강남 성수 왕십리 정자
//        sections.addSection(create_성수역(), create_왕십리역(), LineFixData.createLine_경춘선(), 0);
//
//        assertTrue(sections.getSections().contains(Section.createSection(create_성수역(), create_왕십리역(), LineFixData.createLine_경춘선(), 0)));
//        assertTrue(sections.getSections().contains(Section.createSection(create_왕십리역(), create_정자역(), LineFixData.createLine_경춘선(), 0)));
//    }
}