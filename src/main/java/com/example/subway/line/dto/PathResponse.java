package com.example.subway.line.dto;

import com.example.subway.line.domain.Path;
import com.example.subway.station.dto.StationResponse;

import java.util.List;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;

    public PathResponse(List<StationResponse> stations, int distance) {
        this.stations = stations;
        this.distance = distance;
    }

    public static PathResponse of(Path path) {
        List<StationResponse> stations = path.getStations().stream()
                .map(StationResponse::of)
                .toList();

        int distance = path.extractDistance();

        return new PathResponse(stations, distance);
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }
}
