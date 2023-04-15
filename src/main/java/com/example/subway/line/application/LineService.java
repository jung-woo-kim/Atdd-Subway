package com.example.subway.line.application;

import com.example.subway.line.dao.LineRepository;
import com.example.subway.line.domain.Line;
import com.example.subway.line.dto.LineRequest;
import com.example.subway.line.dto.LineResponse;
import com.example.subway.line.exception.LineDuplicateException;
import com.example.subway.line.exception.LineExceptionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;

    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @Transactional
    public LineResponse saveLine(LineRequest lineRequest) {
        validateExistedLine(lineRequest);
        Line line = lineRepository.save(new Line(lineRequest.getName(), lineRequest.getColor()));

        return LineResponse.of(line);

    }

    private void validateExistedLine(LineRequest lineRequest) {
        Line line = lineRepository.findByName(lineRequest.getName());
        if (line != null) {
            throw new LineDuplicateException(LineExceptionType.LINE_DUPLICATE);
        }
    }

    public List<LineResponse> findAllLines() {
        return lineRepository.findAll().stream().map(LineResponse::of).toList();
    }
}
