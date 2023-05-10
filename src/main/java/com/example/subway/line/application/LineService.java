package com.example.subway.line.application;

import com.example.subway.line.domain.LineRepository;
import com.example.subway.line.domain.Line;
import com.example.subway.line.domain.Section;
import com.example.subway.line.dto.LineRequest;
import com.example.subway.line.dto.LineResponse;
import com.example.subway.line.dto.SectionRequest;
import com.example.subway.line.exception.LineDuplicateException;
import com.example.subway.line.exception.LineExceptionType;
import com.example.subway.line.exception.LineNotExistedException;
import com.example.subway.station.application.StationService;
import com.example.subway.station.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;
    private final StationService stationService;

    public LineService(LineRepository lineRepository, StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    @Transactional
    public LineResponse saveLine(LineRequest request) {
        validateExistedLine(request);
        Line line = lineRepository.save(new Line(request.getName(), request.getColor()));

        if (request.getUpStationId() != null && request.getDownStationId() != null && request.getDistance() != 0) {
            Station upStation = stationService.findById(request.getUpStationId());
            Station downStation = stationService.findById(request.getDownStationId());
            line.addSection(Section.createSection(upStation, downStation, line, request.getDistance()));
        }

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

    public LineResponse findById(Long id) {
        Line line = lineRepository.findById(id).orElseThrow(() -> new LineNotExistedException(LineExceptionType.LINE_NOT_EXIST));
        return LineResponse.of(line);
    }

    // Hibernate의 기본 설정은 dirty read 이기 때문에 save 하지 않아도 업데이트 된다.
    @Transactional
    public void update(Long id, LineRequest lineRequest) {
        Line line = lineRepository.findById(id).orElseThrow(() -> new LineNotExistedException(LineExceptionType.LINE_NOT_EXIST));
        line.update(lineRequest.getName(), lineRequest.getColor());
    }

    @Transactional
    public void delete(Long id) {
        Line line = lineRepository.findById(id).orElseThrow(() -> new LineNotExistedException(LineExceptionType.LINE_NOT_EXIST));
        lineRepository.delete(line);
    }

    @Transactional
    public LineResponse addSection(Long lineId, SectionRequest sectionRequest) {
        Station upStation = stationService.findById(sectionRequest.getUpStationId());
        Station downStation = stationService.findById(sectionRequest.getDownStationId());

        Line line = lineRepository.findById(lineId).orElseThrow(() -> new LineNotExistedException(LineExceptionType.LINE_NOT_EXIST));
        line.addSection(Section.createSection(upStation, downStation, line, sectionRequest.getDistance()));
        return LineResponse.of(line);
    }

    @Transactional
    public void deleteSectionById(Long id, Long stationId) {
        Line line = lineRepository.findById(id).orElseThrow(() -> new LineNotExistedException(LineExceptionType.LINE_NOT_EXIST));
        Station station = stationService.findById(stationId);
        line.deleteSection(station);
    }
}
