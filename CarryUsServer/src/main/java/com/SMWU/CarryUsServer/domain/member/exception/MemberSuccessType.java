package com.SMWU.CarryUsServer.domain.member.exception;

import com.SMWU.CarryUsServer.global.exception.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberSuccessType implements SuccessType {
    MEMBER_PROFILE_GET_SUCCESS(HttpStatus.OK, "사용자 프로필 조회에 성공하였습니다."),
    MEMBER_REVIEW_GET_SUCCESS(HttpStatus.OK, "사용자 리뷰 리스트 조회에 성공하였습니다.");

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
