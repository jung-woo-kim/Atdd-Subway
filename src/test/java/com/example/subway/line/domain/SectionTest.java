package com.example.subway.line.domain;

import com.example.subway.line.acceptance.LineFixData;
import com.example.subway.station.StationFixData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.subway.line.acceptance.LineFixData.*;
import static com.example.subway.station.StationFixData.*;
import static org.junit.jupiter.api.Assertions.*;

class SectionTest {
    private Section section;

    @BeforeEach
    void setUp() {
        section = Section.createSection(create_강남역(), create_성수역(), createLine_경춘선(),1);
    }

    @Test
    void matchUpStation() {
        assertTrue(section.matchUpStation(create_강남역()));
        assertFalse(section.matchUpStation(create_정자역()));
    }

    @Test
    void matchDownStation() {
        assertTrue(section.matchDownStation(create_성수역()));
        assertFalse(section.matchDownStation(create_정자역()));
    }
}