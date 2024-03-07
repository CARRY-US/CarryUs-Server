package com.SMWU.CarryUsServer.domain.store.controller.response;

import com.SMWU.CarryUsServer.domain.store.entity.Store;

import java.util.List;

public record StoreDetailResponseDTO(
        long storeId,
        String storeImgUrl,
        String storeName,
        String storeLocation,
        String closedDay,
        String openingHour,
        String storePhoneNumber,
        List<BaggageTypeInfoResponseDTO> baggageTypeInfoList
) {
    public static StoreDetailResponseDTO of(final Store store, List<BaggageTypeInfoResponseDTO> baggageTypeInfoList){
        return new StoreDetailResponseDTO(store.getStoreId(), store.getStoreImgUrl(), store.getStoreName(), store.getStoreLocation(), store.getClosedDay(), store.getOpeningHour(), store.getStorePhoneNumber(), baggageTypeInfoList);
    }
}
