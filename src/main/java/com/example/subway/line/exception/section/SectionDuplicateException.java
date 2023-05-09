package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;

public class SectionDuplicateException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public SectionDuplicateException(){
        super(SectionExceptionType.SECTION_DUPLICATE.getErrorMessage());
        this.exceptionType = SectionExceptionType.SECTION_DUPLICATE;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
