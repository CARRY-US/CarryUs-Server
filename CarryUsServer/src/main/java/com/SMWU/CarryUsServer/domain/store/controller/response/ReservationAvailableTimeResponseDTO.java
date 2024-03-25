package com.SMWU.CarryUsServer.domain.store.controller.response;

import java.util.List;

public record ReservationAvailableTimeResponseDTO(
        List<Boolean> availableTimeList
) {
    public static ReservationAvailableTimeResponseDTO of(final List<Boolean> availableTimeList){
        return new ReservationAvailableTimeResponseDTO(availableTimeList);
    }
}
