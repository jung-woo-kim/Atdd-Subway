package com.example.subway.common;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private int errorCode;
    private String error;

    public ErrorResponse(LocalDateTime timestamp, int status, int errorCode, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.error = error;
    }

    public static ErrorResponse create(BaseExceptionType exception) {
        return new ErrorResponse(LocalDateTime.now(), exception.getHttpStatus(), exception.getErrorCode(), exception.getErrorMessage());
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getError() {
        return error;
    }
}
