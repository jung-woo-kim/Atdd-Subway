package com.example.subway.line.exception;

import com.example.subway.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LineExceptionAdvice {
    @ExceptionHandler(LineDuplicateException.class)
    public ResponseEntity<ErrorResponse> lineDuplicateException(LineDuplicateException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.create(e.getExceptionType()));
    }
}
