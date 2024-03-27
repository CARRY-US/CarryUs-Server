package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.store.entity.Store;

public record ReservationFilteredByStatusResponseDTO(long reservationId, String storeName, String storeImgUrl, String storeLocation, String reservationInfo, boolean isReviewExist) {
    public static ReservationFilteredByStatusResponseDTO of(final Store store, final Reservation reservation, boolean isReviewExist) {
        return new ReservationFilteredByStatusResponseDTO(reservation.getReservationId(), store.getStoreName(), store.getStoreImgUrl(), store.getStoreLocation(), reservation.getReservationInfo(), isReviewExist);
    }
}
