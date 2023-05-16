package com.example.subway.filter.exception;

import com.example.subway.common.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum AccessTokenExceptionType implements BaseExceptionType {
    INVALID_ACCESS_TOKEN(1001, HttpStatus.NOT_FOUND.value(),"토큰이 유효하지 않습니다.");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    AccessTokenExceptionType(int errorCode, int httpStatus, String errorMessage) {
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
