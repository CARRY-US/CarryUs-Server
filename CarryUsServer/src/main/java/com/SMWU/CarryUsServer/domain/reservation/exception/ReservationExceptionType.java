package com.SMWU.CarryUsServer.domain.reservation.exception;

import com.SMWU.CarryUsServer.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReservationExceptionType implements ExceptionType {
    NOT_COMPLETED_RESERVATION(HttpStatus.BAD_REQUEST, "예약이 완료되지 않았습니다."),
    NOT_FOUND_RESERVATION(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다.");

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
