package com.SMWU.CarryUsServer.domain.auth.exception;

import com.SMWU.CarryUsServer.global.exception.ExceptionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthExceptionType implements ExceptionType {
    INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 플랫폼 인가코드입니다."),
    INVALID_PLATFORM_TYPE(HttpStatus.BAD_REQUEST, "올바르지 않은 플랫폼 유형입니다."),
    INVALID_MEMBER_ROLE(HttpStatus.BAD_REQUEST, "올바르지 않은 사용자 유형입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "서비스에서 발급되지 않은 액세스 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "서비스에서 발급되지 않거나 이미 사용된 리프레시 토큰입니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    UNAUTHORIZED_MEMBER_LOGIN(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
    UNAUTHORIZED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "기한이 만료된 액세스 토큰입니다."),
    UNAUTHORIZED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "기한이 만료된 리프레시 토큰입니다."),
    FORBIDDEN_MEMBER(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");

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
