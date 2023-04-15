package com.example.subway.study;

import com.example.subway.Station;
import com.example.subway.StationRepository;
import com.example.subway.StationResponse;
import com.example.subway.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@DisplayName("단위 테스트 - mockito의 MockitoExtension을 활용한 가짜 협력 객체 사용")
@ExtendWith(MockitoExtension.class)
public class MockExtensionTest {
    @Mock
    private StationRepository stationRepository;

    @Test
    void saveStation() {
        Station station = new Station("강남역");
        when(stationRepository.findAll()).thenReturn(List.of(station));
        StationService stationService = new StationService(stationRepository);
        List<StationResponse> allStations = stationService.findAllStations();
        Assertions.assertEquals(1,allStations.size());
    }
}
