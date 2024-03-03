package com.SMWU.CarryUsServer.domain.reservation.exception;

import com.SMWU.CarryUsServer.global.exception.ClientException;
import com.SMWU.CarryUsServer.global.exception.ExceptionType;

public class ReservationException extends ClientException {
    public ReservationException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}