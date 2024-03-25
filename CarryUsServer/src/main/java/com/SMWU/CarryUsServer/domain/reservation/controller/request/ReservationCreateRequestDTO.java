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
}
