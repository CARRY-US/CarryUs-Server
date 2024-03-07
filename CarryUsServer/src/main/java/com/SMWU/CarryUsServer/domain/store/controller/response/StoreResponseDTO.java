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
    public static StoreResponseDTO of(final Store store, final int reviewCount, final double reviewRatingAverage) {
        return new StoreResponseDTO(store.getStoreId(), store.getStoreImgUrl(), store.getStoreName(), store.getStoreLocation(), reviewCount ,reviewRatingAverage , store.getLatitude(), store.getLongitude());
    }
}
