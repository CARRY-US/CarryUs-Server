package com.SMWU.CarryUsServer.domain.member.controller.response;

import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;

public record MemberReviewResponseDTO(
        long reviewId,
        String storeName,
        double reviewRating,
        String reviewContent) {
    public static MemberReviewResponseDTO of(final ReservationReview reservationReview, final String storeName) {
        return new MemberReviewResponseDTO(reservationReview.getReservationReviewId(), storeName, reservationReview.getReviewRating(), reservationReview.getReviewContent());
    }
}
