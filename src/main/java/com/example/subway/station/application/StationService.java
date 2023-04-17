package com.example.subway.station.application;

import com.example.subway.station.dto.StationRequest;
import com.example.subway.station.dto.StationResponse;
import com.example.subway.station.domain.Station;
import com.example.subway.station.domain.StationRepository;
import com.example.subway.station.exception.StationExceptionType;
import com.example.subway.station.exception.StationNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StationService {
    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    public StationResponse saveStation(StationRequest stationRequest) {
        Station station = stationRepository.save(new Station(stationRequest.getName()));
        return createStationResponse(station);
    }

    public List<StationResponse> findAllStations() {
        return stationRepository.findAll().stream()
                .map(this::createStationResponse)
                .collect(Collectors.toList());
    }

    public Station findById(Long id) {
        return stationRepository.findById(id).orElseThrow(() -> new StationNotFoundException(StationExceptionType.STATION_NOT_EXIST));
    }

    @Transactional
    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
    }

    private StationResponse createStationResponse(Station station) {
        return new StationResponse(
                station.getId(),
                station.getName()
        );
    }
}
