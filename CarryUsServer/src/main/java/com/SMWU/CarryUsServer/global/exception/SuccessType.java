package com.SMWU.CarryUsServer.global.exception;

import org.springframework.http.HttpStatus;

public interface SuccessType {
    HttpStatus status();

    String message();

    default int getHttpStatusCode() {
        return status().value();
    }
}
