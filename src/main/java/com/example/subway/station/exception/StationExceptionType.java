package com.example.subway.station.exception;

import com.example.subway.common.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum StationExceptionType implements BaseExceptionType {
    STATION_DUPLICATE(1001, HttpStatus.BAD_REQUEST.value(), "이미 해당하는 역의 이름이 존재합니다."),
    STATION_NOT_EXIST(1002,HttpStatus.NOT_FOUND.value(), "역이 존재하지 않습니다.");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    StationExceptionType(int errorCode, int httpStatus, String errorMessage) {
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
