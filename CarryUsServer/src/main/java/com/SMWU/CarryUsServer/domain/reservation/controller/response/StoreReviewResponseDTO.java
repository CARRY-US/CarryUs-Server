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
    public static StoreReviewResponseDTO of(long reviewId, String memberName, String reviewCreatedAt, double reviewRating, String reviewText) {
        return new StoreReviewResponseDTO(reviewId, memberName, reviewCreatedAt, reviewRating, reviewText);
    }

    public static StoreReviewResponseDTO getStoreReviewResponseDTO(final ReservationReview reservationReview){
        return StoreReviewResponseDTO.of(reservationReview.getReservationReviewId(), reservationReview.getReservation().getClient().getName(), getReviewCreatedAt(reservationReview.getCreatedAt()), reservationReview.getReviewRating(), reservationReview.getReviewContent());
    }

    private static String getReviewCreatedAt(final LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
