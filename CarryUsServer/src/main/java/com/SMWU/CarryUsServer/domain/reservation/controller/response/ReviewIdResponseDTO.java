package com.SMWU.CarryUsServer.domain.reservation.controller.response;

public record ReviewIdResponseDTO(long reviewId) {
    public static ReviewIdResponseDTO of(long reviewId) {
        return new ReviewIdResponseDTO(reviewId);
    }
}
