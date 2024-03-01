package com.SMWU.CarryUsServer.domain.member.controller.response;

public record MemberReviewResponseDTO(
        long reviewId,
        String storeName,
        double reviewRating,
        String reviewContent) {
    public static MemberReviewResponseDTO of(long reviewId, String storeName, double reviewRating, String reviewContent) {
        return new MemberReviewResponseDTO(reviewId, storeName, reviewRating, reviewContent);
    }
}
