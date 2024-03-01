package com.SMWU.CarryUsServer.domain.reservation.controller.response;

public record ReviewResponseDTO(
        long reviewId,
        double reviewRating,
        String reviewContent) {
    public static ReviewResponseDTO of(long reviewId, double reviewRating, String reviewContent) {
        return new ReviewResponseDTO(reviewId, reviewRating, reviewContent);
    }
}
