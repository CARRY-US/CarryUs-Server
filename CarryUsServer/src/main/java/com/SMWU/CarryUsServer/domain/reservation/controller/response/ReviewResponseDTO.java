package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;

public record ReviewResponseDTO(
        long reviewId,
        double reviewRating,
        String reviewContent) {
    public static ReviewResponseDTO of(final ReservationReview reservationReview){
        return new ReviewResponseDTO(reservationReview.getReservationReviewId(), reservationReview.getReviewRating(), reservationReview.getReviewContent());
    }
}
