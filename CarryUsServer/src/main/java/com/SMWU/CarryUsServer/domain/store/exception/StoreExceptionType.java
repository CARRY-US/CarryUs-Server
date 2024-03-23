package com.SMWU.CarryUsServer.domain.store.exception;

import com.SMWU.CarryUsServer.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum StoreExceptionType implements ExceptionType {
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다"),
    NOT_FOUND_STORE_BAGGAGE(HttpStatus.NOT_FOUND, "가게에 해당하는 캐리어를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
