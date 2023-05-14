package com.example.subway.station.exception;

import com.example.subway.common.BaseExceptionType;

public class StationNotFoundException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public StationNotFoundException(){
        super(StationExceptionType.STATION_NOT_EXIST.getErrorMessage());
        this.exceptionType = StationExceptionType.STATION_NOT_EXIST;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
