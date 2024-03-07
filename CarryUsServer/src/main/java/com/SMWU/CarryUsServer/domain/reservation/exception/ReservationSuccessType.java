package com.SMWU.CarryUsServer.domain.reservation.exception;

import com.SMWU.CarryUsServer.global.exception.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReservationSuccessType implements SuccessType {
    RESERVATION_MEMBER_DEFAULT_INFO_GET_SUCCESS(HttpStatus.OK, "회원의 기본 예약 정보 조회에 성공하였습니다.");

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
