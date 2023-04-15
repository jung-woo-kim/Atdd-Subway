package com.example.subway.line.application;

import com.example.subway.line.dao.LineRepository;
import com.example.subway.line.domain.Line;
import com.example.subway.line.dto.LineRequest;
import com.example.subway.line.dto.LineResponse;
import com.example.subway.line.exception.LineDuplicateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {
    @Mock
    private LineRepository lineRepository;

    @InjectMocks
    private LineService lineService;

    @Test
    void saveLine() {
        //given
        Line line = new Line("2호선", "green");
        LineRequest lineRequest = new LineRequest("2호선", "green");

        //when
        when(lineRepository.save(line)).thenReturn(new Line("2호선", "green"));

        //then
        LineResponse lineResponse = lineService.saveLine(lineRequest);
        assertEquals("2호선",lineResponse.getName());
    }

    @Test
    void saveSameLine() {
        //given
        Line lineGreen = new Line("2호선", "green");
        Line lineRed = new Line("2호선", "red");

        LineRequest lineRequestGreen = new LineRequest("2호선", "green");
        LineRequest lineRequestRed = new LineRequest("2호선", "red");


        //when
        when(lineRepository.save(lineGreen)).thenReturn(new Line("2호선", "green"));

        lineService.saveLine(lineRequestGreen);
        when(lineRepository.findByName("2호선")).thenReturn(new Line("2호선", "green"));

        //then
        assertThrows(LineDuplicateException.class,() ->lineService.saveLine(lineRequestRed));
    }
}