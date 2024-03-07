package com.SMWU.CarryUsServer.domain.reservation.exception;

import com.SMWU.CarryUsServer.global.exception.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReviewSuccessType implements SuccessType {
    REVIEW_GET_SUCCESS(HttpStatus.OK, "리뷰 상세 조회에 성공하였습니다."),
    REVIEW_STORE_INFO_GET_SUCCESS(HttpStatus.OK, "리뷰를 남긴 가게에 대한 정보 조회에 성공하였습니다."),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "리뷰 수정에 성공하였습니다."),
    REVIEW_CREATE_SUCCESS(HttpStatus.CREATED, "리뷰 작성에 성공하였습니다."),
    REVIEW_BY_STATUS_GET_SUCCESS(HttpStatus.OK, "리뷰 상태별 조회에 성공하였습니다.");

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
