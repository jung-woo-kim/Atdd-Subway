package com.example.subway.line.acceptance;

import java.util.HashMap;
import java.util.Map;

public class LineFixData {

    private static Map<String, String> 경춘선;
    private static Map<String, String> 신분당선;

    private static final String 경춘 = "경춘선";
    private static final String 초록색 = "green";
    private static final String 신분당 = "신분당선";
    private static final String 빨간색 = "red";

    public static Map<String,String> create_경춘선() {
        경춘선 = new HashMap<>();

        경춘선.put("name", 경춘);
        경춘선.put("color", 초록색);

        return 경춘선;
    }

    public static Map<String,String> create_신분당선() {
        신분당선 = new HashMap<>();

        신분당선.put("name", 신분당);
        신분당선.put("color", 빨간색);

        return 신분당선;
    }
}
