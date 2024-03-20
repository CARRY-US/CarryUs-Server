package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.store.entity.Store;

public record ReservationDetailResponseDTO(
        Long reservationId,
        Long storeId,
        String storeImgUrl,
        String storeName,
        String reservationType,
        String reservationDate,
        String reservationBaggageInfo,
        String memberName,
        String memberPhoneNumber,
        String reservationRequest,
        int reservationPayment
) {
    public static ReservationDetailResponseDTO of(final Store store, final Reservation reservation, final Member member, final String reservationBaggageInfo, final int reservationPayment){
        return new ReservationDetailResponseDTO(
                reservation.getReservationId(),
                store.getStoreId(),
                store.getStoreImgUrl(),
                store.getStoreName(),
                reservation.getReservationType().getStatus(),
                reservation.getReservationInfo(),
                reservationBaggageInfo,
                member.getName(),
                member.getPhoneNumber(),
                reservation.getClientRequestContent(),
                reservationPayment
                );
    }
}
