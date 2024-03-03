package com.SMWU.CarryUsServer.domain.store.controller.response;

public record StoreResponseDTO(
        long storeId,
        String storeImgUrl,
        String storeName,
        String storeLocation,
        int storeReviewCount,
        double storeRatingAverage,
        double latitude,
        double longitude) {
    public static StoreResponseDTO of(long storeId, String storeImgUrl, String storeName, String storeLocation, int storeReviewCount, double storeRatingAverage, double latitude, double longitude){
        return new StoreResponseDTO(storeId, storeImgUrl, storeName, storeLocation, storeReviewCount, storeRatingAverage, latitude, longitude);
    }
}
