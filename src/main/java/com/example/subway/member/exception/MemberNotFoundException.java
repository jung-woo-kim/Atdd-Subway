package com.example.subway.member.exception;

import com.example.subway.common.BaseExceptionType;

public class MemberNotFoundException extends RuntimeException{
    private final BaseExceptionType exceptionType;

    public MemberNotFoundException() {
        super(MemberExceptionType.MEMBER_NOT_FOUND.getErrorMessage());
        this.exceptionType = MemberExceptionType.MEMBER_NOT_FOUND;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
