package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;

public class SectionNotExistedException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public SectionNotExistedException(BaseExceptionType exceptionType){
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
