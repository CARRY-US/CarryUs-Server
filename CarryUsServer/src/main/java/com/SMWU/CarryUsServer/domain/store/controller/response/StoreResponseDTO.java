package com.SMWU.CarryUsServer.domain.store.controller.response;

import com.SMWU.CarryUsServer.domain.store.entity.Store;

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

    public static StoreResponseDTO toStoreResponseDTO(final Store store, final int reviewCount, final double reviewRatingAverage) {
        return StoreResponseDTO.of(store.getStoreId(), store.getStoreImgUrl(), store.getStoreName(), store.getStoreLocation(), reviewCount ,reviewRatingAverage , store.getLatitude(), store.getLongitude());
    }
}
