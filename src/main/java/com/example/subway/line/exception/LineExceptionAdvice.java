package com.example.subway.line.exception;

import com.example.subway.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LineExceptionAdvice {
    @ExceptionHandler(LineDuplicateException.class)
    public ResponseEntity<ErrorResponse> lineDuplicateException(LineDuplicateException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.create(e.getExceptionType()));
    }

    @ExceptionHandler(LineNotExistedException.class)
    public ResponseEntity<ErrorResponse> lineNotExistedException(LineNotExistedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.create(e.getExceptionType()));
    }
}
