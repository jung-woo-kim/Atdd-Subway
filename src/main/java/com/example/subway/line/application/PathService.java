package com.example.subway.line.application;

import com.example.subway.line.domain.Line;
import com.example.subway.line.domain.Path;
import com.example.subway.line.domain.SubwayMap;
import com.example.subway.line.dto.PathResponse;
import com.example.subway.station.application.StationService;
import com.example.subway.station.domain.Station;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathService {
    private LineService lineService;
    private StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    public PathResponse findPath(Long source, Long target) {
        Station upStation = stationService.findById(source);
        Station downStation = stationService.findById(target);
        List<Line> lines = lineService.findLines();
        SubwayMap subwayMap = new SubwayMap(lines);
        Path path = subwayMap.findPath(upStation, downStation);

        return PathResponse.of(path);
    }
}
