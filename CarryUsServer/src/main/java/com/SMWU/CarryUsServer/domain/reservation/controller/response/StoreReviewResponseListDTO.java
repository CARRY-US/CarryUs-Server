package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import java.util.List;

public record StoreReviewResponseListDTO(
        double reviewRatingAverage,
        List<StoreReviewResponseDTO> reviewList
) {
    public static StoreReviewResponseListDTO of(double reviewRatingAverage, List<StoreReviewResponseDTO> reviewList) {
        return new StoreReviewResponseListDTO(reviewRatingAverage, reviewList);
    }
}
