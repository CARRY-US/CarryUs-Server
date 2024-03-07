package com.SMWU.CarryUsServer.domain.reservation.controller.request;

public record ReservationCancelRequestDTO(long reservationId, String cancelReason) {
}
