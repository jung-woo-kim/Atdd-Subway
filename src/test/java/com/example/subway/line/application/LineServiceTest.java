package com.example.subway.line.application;

import com.example.subway.line.dao.LineRepository;
import com.example.subway.line.domain.Line;
import com.example.subway.line.dto.LineRequest;
import com.example.subway.line.dto.LineResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {
    @Mock
    private LineRepository lineRepository;

    @Test
    void saveLine() {
        //given
        Line line = new Line("2호선", "green");
        LineRequest lineRequest = new LineRequest("2호선", "green");

        //when
        when(lineRepository.save(line)).thenReturn(new Line("2호선", "green"));
        LineService lineService = new LineService(lineRepository);

        //then
        LineResponse lineResponse = lineService.saveLine(lineRequest);

        Assertions.assertEquals("2호선",lineResponse.getName());
    }
}