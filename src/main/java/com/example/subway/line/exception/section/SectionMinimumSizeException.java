package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;

public class SectionMinimumSizeException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public SectionMinimumSizeException(){
        super(SectionExceptionType.SECTION_MINIMUM_SIZE.getErrorMessage());
        this.exceptionType = SectionExceptionType.SECTION_MINIMUM_SIZE;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
