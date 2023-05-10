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
    @ExceptionHandler(LineDuplicateException.class)
    public ResponseEntity<ErrorResponse> sectionDuplicateException(SectionDuplicateException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.create(e.getExceptionType()));
    }

    @ExceptionHandler(LineNotExistedException.class)
    public ResponseEntity<ErrorResponse> sectioNotExistedException(SectionNotExistedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.create(e.getExceptionType()));
    }
}
