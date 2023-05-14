package com.example.subway.line.exception.section;

import com.example.subway.common.ErrorResponse;
import com.example.subway.line.exception.LineDuplicateException;
import com.example.subway.line.exception.LineNotExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SectionExceptionAdvice {
    @ExceptionHandler({SectionDuplicateException.class, SectionMinimumSizeException.class, SectionDistanceExceedException.class, SectionNotLastStationException.class})
    public ResponseEntity<ErrorResponse> sectionDuplicateException(SectionDuplicateException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.create(e.getExceptionType()));
    }

    @ExceptionHandler(SectionNotExistedException.class)
    public ResponseEntity<ErrorResponse> sectionNotExistedException(SectionNotExistedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.create(e.getExceptionType()));
    }
}
