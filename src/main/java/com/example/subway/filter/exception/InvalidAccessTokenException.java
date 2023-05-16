package com.example.subway.filter.exception;

import com.example.subway.common.BaseExceptionType;

public class InvalidAccessTokenException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public InvalidAccessTokenException(BaseExceptionType exceptionType) {
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
