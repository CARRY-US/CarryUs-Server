package com.SMWU.CarryUsServer.global.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionType {
    HttpStatus status();
    String message();

    default int getHttpStatusCode() {
        return status().value();
    }
}
