package com.SMWU.CarryUsServer.domain.store.exception;

import com.SMWU.CarryUsServer.global.exception.ClientException;
import com.SMWU.CarryUsServer.global.exception.ExceptionType;

public class StoreException extends ClientException {
    public StoreException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
