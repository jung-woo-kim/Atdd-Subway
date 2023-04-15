package com.example.subway.line.ui;

import com.example.subway.line.application.LineService;
import com.example.subway.line.dto.LineRequest;
import com.example.subway.line.dto.LineResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class LineController {
    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest lineRequest) {
        LineResponse line = lineService.saveLine(lineRequest);
        return ResponseEntity.created(URI.create("/lines" + line.getId())).body(line);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> showLines() {
        List<LineResponse> allLines = lineService.findAllLines();
        return ResponseEntity.ok().body(allLines);
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<LineResponse> showLines(@PathVariable Long id) {
        LineResponse line = lineService.findById(id);
        return ResponseEntity.ok().body(line);
    }
}
