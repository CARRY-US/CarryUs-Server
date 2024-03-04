package com.SMWU.CarryUsServer.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonExceptionType implements ExceptionType {
    NOT_FOUND_PATH(HttpStatus.NOT_FOUND, "요청하신 경로를 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
    INVALID_JSON_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "올바른 요청 형식이 아닙니다")
    ;

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
