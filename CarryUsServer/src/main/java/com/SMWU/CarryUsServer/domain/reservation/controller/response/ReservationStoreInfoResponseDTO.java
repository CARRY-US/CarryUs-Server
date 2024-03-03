package com.SMWU.CarryUsServer.domain.reservation.controller.response;

public record ReservationStoreInfoResponseDTO(long storeId, String storeName, String storeLocation, String reservationInfo) {
    public static ReservationStoreInfoResponseDTO of(long storeId, String storeName, String storeLocation, String reservationInfo) {
        return new ReservationStoreInfoResponseDTO(storeId, storeName, storeLocation, reservationInfo);
    }
}