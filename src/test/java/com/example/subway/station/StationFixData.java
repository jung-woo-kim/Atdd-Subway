package com.example.subway.station;

import java.util.HashMap;
import java.util.Map;

public class StationFixData {
    private static final Map<String, String> 강남역 = new HashMap<>();
    private static final Map<String, String> 성수역 = new HashMap<>();
    private static final Map<String, String> 정자역 = new HashMap<>();

    public static final String 강남역_이름 = "강남역";
    private static final String 성수역_이름 = "성수역";
    private static final String 정자역_이름 = "정자역";


    public static Map<String,String> create_강남역() {
        강남역.put("name", 강남역_이름);

        return 강남역;
    }

    public static Map<String,String> create_성수역() {
        성수역.put("name", 성수역_이름);

        return 성수역;
    }

    public static Map<String,String> create_정자역() {
        정자역.put("name", 정자역_이름);

        return 정자역;
    }
}
