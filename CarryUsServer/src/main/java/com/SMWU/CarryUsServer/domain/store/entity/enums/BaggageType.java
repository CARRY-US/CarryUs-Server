package com.SMWU.CarryUsServer.domain.store.entity.enums;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

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

    public static ConcurrentHashMap<BaggageType, Integer> createBaggageCountMap(final int extraSmallCount, final int smallCount, final int largeCount, final int extraLargeCount){
        ConcurrentHashMap<BaggageType, Integer> baggageCount = new ConcurrentHashMap<>();
        baggageCount.put(BaggageType.EXTRASMALL, extraSmallCount);
        baggageCount.put(BaggageType.SMALL, smallCount);
        baggageCount.put(BaggageType.LARGE, largeCount);
        baggageCount.put(BaggageType.EXTRALARGE, extraLargeCount);
        return baggageCount;
    }
}