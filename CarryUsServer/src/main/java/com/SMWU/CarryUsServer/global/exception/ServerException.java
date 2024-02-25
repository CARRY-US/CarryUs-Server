package com.SMWU.CarryUsServer.global.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException{
    private final ExceptionType exceptionType;

    public ServerException(ExceptionType exceptionType) {
        super(exceptionType.message());
        this.exceptionType = exceptionType;
    }
}
