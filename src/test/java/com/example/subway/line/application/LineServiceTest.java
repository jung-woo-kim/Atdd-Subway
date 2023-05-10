package com.example.subway.line.application;

import com.example.subway.line.domain.LineRepository;
import com.example.subway.line.domain.Line;
import com.example.subway.line.dto.LineRequest;
import com.example.subway.line.dto.LineResponse;
import com.example.subway.line.exception.LineDuplicateException;
import com.example.subway.line.exception.LineNotExistedException;
import com.example.subway.station.StationFixData;
import com.example.subway.station.application.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.subway.line.acceptance.LineFixData.*;
import static com.example.subway.line.acceptance.LineFixData.createLine_경춘선;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {
    @Mock
    private LineRepository lineRepository;

    @Mock
    private StationService stationService;

    @InjectMocks
    private LineService lineService;

    private Line 경춘선;
    private Line 신분당선;
    private LineRequest 경춘선_요청;
    private LineRequest 신분당선_요청;

    @BeforeEach
    void setUp() {
        경춘선 = createLine_경춘선();
        신분당선 = createLine_신분당();
        경춘선_요청 = createLineRequest_경춘선();
        신분당선_요청 = createLineRequest_신분당선();
    }

    @DisplayName("한 노선을 저장한다.")
    @Test
    void saveLine() {
        //when
        when(stationService.findById(1L)).thenReturn(StationFixData.create_강남역());
        when(stationService.findById(2L)).thenReturn(StationFixData.create_성수역());
        when(lineRepository.save(경춘선)).thenReturn(createEmptyLine_경춘선());

        //then
        LineResponse lineResponse = lineService.saveLine(경춘선_요청);
        assertEquals(경춘, lineResponse.getName());
    }

    @DisplayName("중복된 이름의 노선을 저장한다.")
    @Test
    void saveSameLine() {

        //when
        when(stationService.findById(1L)).thenReturn(StationFixData.create_강남역());
        when(stationService.findById(2L)).thenReturn(StationFixData.create_성수역());
        when(lineRepository.save(경춘선)).thenReturn(createEmptyLine_경춘선());

        lineService.saveLine(경춘선_요청);
        when(lineRepository.findByName(경춘)).thenReturn(createLine_경춘선());

        //then
        assertThrows(LineDuplicateException.class, () -> lineService.saveLine(경춘선_요청));
    }

    @DisplayName("저장된 노선 목록을 조회한다.")
    @Test
    void findAllLines() {

        //when
        when(lineRepository.findAll()).thenReturn(List.of(경춘선, 신분당선));

        //then
        List<LineResponse> lines = lineService.findAllLines();
        assertTrue(lines.contains(LineResponse.of(경춘선)));
        assertTrue(lines.contains(LineResponse.of(신분당선)));
    }

    @DisplayName("저장된 노선을 조회한다.")
    @Test
    void findLine() {
        //given
        Long id = 1L;
        //when
        when(lineRepository.findById(id)).thenReturn(Optional.of(경춘선));

        //then
        assertEquals(LineResponse.of(경춘선), lineService.findById(id));
    }

    @DisplayName("저장되지 않은 노선을 조회한다.")
    @Test
    void findNotExistedLine() {
        //given
        Long id = 10L;
        //when
        when(lineRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThrows(LineNotExistedException.class, () -> lineService.findById(id));
    }

    @DisplayName("노선을 업데이트 한다.")
    @Test
    void updateLine() {
        //given

        Long id = 1L;
        LineRequest lineRequest = new LineRequest("1호선", "orange", 1L, 2L, 3);
        //when
        when(lineRepository.findById(id)).thenReturn(Optional.of(경춘선));
        lineService.update(id, lineRequest);
        //then
        assertEquals("orange", 경춘선.getColor());
        assertEquals("1호선", 경춘선.getName());
    }

    @DisplayName("존재하지 않는 노선을 업데이트 한다.")
    @Test
    void updateNotExistedLine() {
        //given
        Long id = 1L;
        //when
        when(lineRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(LineNotExistedException.class, () -> lineService.update(id, 경춘선_요청));
    }

    @DisplayName("존재하지 않는 노선을 삭제한다.")
    @Test
    void deleteLine() {
        //given
        Long id = 1L;
        //when
        when(lineRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(LineNotExistedException.class, () -> lineService.delete(id));
    }
}