package com.example.subway.line.exception.section;

import com.example.subway.common.BaseExceptionType;

public class SectionDuplicateException extends RuntimeException{
    private BaseExceptionType exceptionType;

    public SectionDuplicateException(BaseExceptionType exceptionType){
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
