package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;

public class SectionDistanceExceedException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public SectionDistanceExceedException(){
        super(SectionExceptionType.SECTION_DISTANCE_EXCEED.getErrorMessage());
        this.exceptionType = SectionExceptionType.SECTION_DISTANCE_EXCEED;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
