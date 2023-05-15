package com.example.subway.member.exception;

import com.example.subway.common.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    MEMBER_NOT_FOUND(1001, HttpStatus.NOT_FOUND.value(),"유저가 존재하지 않습니다.");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, int httpStatus, String errorMessage) {
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
