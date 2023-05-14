package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum SectionExceptionType implements BaseExceptionType {
    SECTION_DUPLICATE(1001, HttpStatus.BAD_REQUEST.value(), "이미 해당하는 상행역, 하행역이 존재합니다."),
    SECTION_NOT_EXIST(1002, HttpStatus.NOT_FOUND.value(), "상행역 하행역이 존재하지 않습니다."),
    SECTION_NOT_LAST_STATION(1003, HttpStatus.BAD_REQUEST.value(), "마지막 구간이 아닙니다."),
    SECTION_MINIMUM_SIZE(1004, HttpStatus.BAD_REQUEST.value(), "구간이 현재 최소 크기입니다."),
    SECTION_DISTANCE_EXCEED(1005, HttpStatus.BAD_REQUEST.value(), "구간의 사이 거리가 새롭게 들어오는 구간의 거리보다 작습니다.");

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
