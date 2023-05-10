package com.example.subway.line.acceptance;

import com.example.subway.line.domain.Line;
import com.example.subway.line.domain.Section;
import com.example.subway.line.dto.LineRequest;
import com.example.subway.station.StationFixData;

import java.util.HashMap;
import java.util.Map;

public class LineFixData {

    public static final String 경춘 = "경춘선";
    private static final String 초록색 = "green";
    private static final String 신분당 = "신분당선";
    private static final String 빨간색 = "red";
    private static final Long upStationId = 1L;
    private static final Long downStationId = 2L;
    private static final int distance = 10;

    public static Line createLine_경춘선() {
        Line line = new Line(경춘, 초록색);
        line.addSection(Section.createSection(StationFixData.create_강남역(),StationFixData.create_성수역(),line,distance));
        return line;
    }

    public static Line createLine_신분당() {
        Line line = new Line(신분당, 빨간색);
        line.addSection(Section.createSection(StationFixData.create_강남역(),StationFixData.create_성수역(),line,distance));
        return line;
    }

    public static LineRequest createLineRequest_경춘선() {
        return new LineRequest(경춘, 초록색, upStationId, downStationId, distance);
    }

    public static LineRequest createLineRequest_신분당선() {
        return new LineRequest(신분당, 빨간색, upStationId, downStationId, distance);
    }


    public static Map<String, String> create_경춘선_params() {
        Map<String, String> 경춘선 = new HashMap<>();

        경춘선.put("name", 경춘);
        경춘선.put("color", 초록색);
        경춘선.put("upStationId", upStationId.toString());
        경춘선.put("downStationId", downStationId.toString());
        경춘선.put("distance", distance + "");

        return 경춘선;
    }

    public static Map<String, String> create_신분당선_params() {
        Map<String, String> 신분당선 = new HashMap<>();

        신분당선.put("name", 신분당);
        신분당선.put("color", 빨간색);
        신분당선.put("upStationId", upStationId.toString());
        신분당선.put("downStationId", downStationId.toString());
        신분당선.put("distance", distance + "");

        return 신분당선;
    }
}
