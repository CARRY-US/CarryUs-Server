package com.SMWU.CarryUsServer.domain.reservation.exception;

import com.SMWU.CarryUsServer.global.exception.ClientException;
import com.SMWU.CarryUsServer.global.exception.ExceptionType;

public class ReviewException extends ClientException {
    public ReviewException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
