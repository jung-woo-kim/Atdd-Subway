package com.example.subway.line.exception;

import com.example.subway.common.BaseExceptionType;

public class LineNotExistedException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public LineNotExistedException(BaseExceptionType exceptionType){
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
