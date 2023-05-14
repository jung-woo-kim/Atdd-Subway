package com.example.subway.line.acceptance;

import com.example.subway.line.domain.Line;
import com.example.subway.line.domain.Section;

import static com.example.subway.station.StationFixData.*;

public class SectionFixData {

    private static final Line line = LineFixData.createLine_경춘선();
    private static final int distance = 6;

    public static Section 강남_성수() {
        return Section.createSection(create_강남역(), create_성수역(), line, 100);
    }

    public static Section 성수_왕십리() {
        return Section.createSection(create_성수역(), create_왕십리역(), line, distance);
    }

    public static Section 강남_왕십리() {
        return Section.createSection(create_강남역(), create_왕십리역(), line, distance);
    }

    public static Section 성수_정자() {
        return Section.createSection(create_성수역(), create_정자역(), line, 100);
    }

    public static Section 성수_강남() {
        return Section.createSection(create_성수역(), create_강남역(), line, distance);

    }
}
