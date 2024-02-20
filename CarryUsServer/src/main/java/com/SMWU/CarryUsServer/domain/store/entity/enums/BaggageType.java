package com.SMWU.CarryUsServer.domain.store.entity.enums;

import lombok.Getter;

@Getter
public enum BaggageType {
    EXTRASMALL("20인치 미만"),
    SMALL("20인치"),
    LARGE("24인치"),
    EXTRALARGE("28인치 이상");

    private String message;

    BaggageType(String message) {
        this.message = message;
    }
}