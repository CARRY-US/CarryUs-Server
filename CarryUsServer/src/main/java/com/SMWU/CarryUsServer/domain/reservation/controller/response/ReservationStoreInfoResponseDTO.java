package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import com.SMWU.CarryUsServer.domain.store.entity.Store;

public record ReservationStoreInfoResponseDTO(long storeId, String storeName, String storeImgUrl, String storeLocation, String reservationInfo) {
    public static ReservationStoreInfoResponseDTO of(final Store store, final String reservationInfo) {
        return new ReservationStoreInfoResponseDTO(store.getStoreId(), store.getStoreName(), store.getStoreImgUrl(), store.getStoreLocation(), reservationInfo);
    }
}