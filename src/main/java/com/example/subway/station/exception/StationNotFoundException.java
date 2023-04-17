package com.example.subway.station.exception;

import com.example.subway.common.BaseExceptionType;

public class StationNotFoundException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public StationNotFoundException(BaseExceptionType exceptionType){
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
