package com.SMWU.CarryUsServer.domain.reservation.controller.response;

public record ReservationIdResponseDTO(long reservationId) {
    public static ReservationIdResponseDTO of(long reservationId) {
        return new ReservationIdResponseDTO(reservationId);
    }
}
