package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record StoreReviewResponseDTO(
        long reviewId,
        String memberName,
        String reviewCreatedAt,
        double reviewRating,
        String reviewText
        ) {
    public static StoreReviewResponseDTO of(final ReservationReview reservationReview){
        String memberName = reservationReview.getReservation().getClient().getName();
        String reviewCreatedAt = getReviewCreatedAt(reservationReview.getCreatedAt());
        return new StoreReviewResponseDTO(reservationReview.getReservationReviewId(), memberName, reviewCreatedAt, reservationReview.getReviewRating(), reservationReview.getReviewContent());
    }

    private static String getReviewCreatedAt(final LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
