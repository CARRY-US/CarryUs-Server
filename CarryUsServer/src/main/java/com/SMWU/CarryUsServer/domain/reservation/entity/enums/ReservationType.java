package com.SMWU.CarryUsServer.domain.reservation.entity.enums;

import lombok.Getter;

@Getter
public enum ReservationType {
    ACCEPTED("수락"), CANCELED("취소"), WAITING("대기"), COMPLETED("완료");

    private String status;

    ReservationType(String status) {
        this.status = status;
    }
}
