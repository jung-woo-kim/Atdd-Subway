package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum SectionExceptionType implements BaseExceptionType {
    SECTION_DUPLICATE(1001, HttpStatus.BAD_REQUEST.value(), "이미 해당하는 상행역, 하행역이 존재합니다."),
    SECTION_NOT_EXIST(1002,HttpStatus.NOT_FOUND.value(), "상행역 하행역이 존재하지 않습니다.");

    private final int errorCode;
    private final int httpStatus;
    private final String errorMessage;

    SectionExceptionType(int errorCode, int httpStatus, String errorMessage) {
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
