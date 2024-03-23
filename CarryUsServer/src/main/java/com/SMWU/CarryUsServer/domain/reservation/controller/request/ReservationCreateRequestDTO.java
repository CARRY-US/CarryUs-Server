package com.SMWU.CarryUsServer.domain.reservation.controller.request;

import com.SMWU.CarryUsServer.domain.store.entity.enums.BaggageType;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public record ReservationCreateRequestDTO(
        int extraSmallCount,
        int smallCount,
        int largeCount,
        int extraLargeCount,
        LocalDateTime reservationStartTime,
        LocalDateTime reservationEndTime,
        String memberName,
        String memberPhoneNumber,
        String memberRequestContent
) {
    public static ConcurrentHashMap<BaggageType, Integer> createBaggageCountMap(final ReservationCreateRequestDTO request){
        ConcurrentHashMap<BaggageType, Integer> baggageCount = new ConcurrentHashMap<>();
        baggageCount.put(BaggageType.EXTRASMALL, request.extraSmallCount());
        baggageCount.put(BaggageType.SMALL, request.smallCount());
        baggageCount.put(BaggageType.LARGE, request.largeCount());
        baggageCount.put(BaggageType.EXTRALARGE, request.extraLargeCount());
        return baggageCount;
    }
}
