package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;

public class SectionNotLastStationException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public SectionNotLastStationException(){
        super(SectionExceptionType.SECTION_NOT_LAST_STATION.getErrorMessage());
        this.exceptionType = SectionExceptionType.SECTION_NOT_LAST_STATION;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
