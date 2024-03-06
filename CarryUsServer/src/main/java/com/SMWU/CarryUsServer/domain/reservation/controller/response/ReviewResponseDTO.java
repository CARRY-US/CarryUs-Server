package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;

public record ReviewResponseDTO(
        long reviewId,
        double reviewRating,
        String reviewContent) {
    public static ReviewResponseDTO of(long reviewId, double reviewRating, String reviewContent) {
        return new ReviewResponseDTO(reviewId, reviewRating, reviewContent);
    }

    public static ReviewResponseDTO toReviewResponseDTO(final ReservationReview reservationReview){
        return ReviewResponseDTO.of(reservationReview.getReservationReviewId(), reservationReview.getReviewRating(), reservationReview.getReviewContent());
    }
}
