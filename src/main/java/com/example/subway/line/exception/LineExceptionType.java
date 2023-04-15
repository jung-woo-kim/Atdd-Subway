package com.example.subway.line.exception;

import com.example.subway.common.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum LineExceptionType implements BaseExceptionType {
    LINE_DUPLICATE(1001, HttpStatus.BAD_REQUEST.value(), "이미 해당하는 라인의 이름이 존재합니다.");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    LineExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
