package com.example.subway.line.exception;

import com.example.subway.common.BaseExceptionType;

public class LineDuplicateException extends RuntimeException{
    private BaseExceptionType exceptionType;

    public LineDuplicateException(BaseExceptionType exceptionType){
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
