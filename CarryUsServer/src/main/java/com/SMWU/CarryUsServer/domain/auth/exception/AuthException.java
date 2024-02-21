package com.SMWU.CarryUsServer.domain.auth.exception;

import com.SMWU.CarryUsServer.global.exception.ClientException;
import com.SMWU.CarryUsServer.global.exception.ExceptionType;

public class AuthException extends ClientException {
    public AuthException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
