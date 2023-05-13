package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;

public class SectionNotExistedException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public SectionNotExistedException(){
        super(SectionExceptionType.SECTION_NOT_EXIST.getErrorMessage());
        this.exceptionType = SectionExceptionType.SECTION_NOT_EXIST;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
