package com.SMWU.CarryUsServer.domain.member.controller.response;

import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;

public record MemberReviewResponseDTO(
        long reviewId,
        String storeName,
        double reviewRating,
        String reviewContent) {
    public static MemberReviewResponseDTO of(long reviewId, String storeName, double reviewRating, String reviewContent) {
        return new MemberReviewResponseDTO(reviewId, storeName, reviewRating, reviewContent);
    }

    public static MemberReviewResponseDTO toMemberReviewResponseDTO(final ReservationReview reservationReview, final String storeName) {
        return MemberReviewResponseDTO.of(reservationReview.getReservationReviewId(), storeName, reservationReview.getReviewRating(), reservationReview.getReviewContent());
    }
}
