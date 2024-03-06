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
    public static StoreDetailResponseDTO of(long storeId, String storeImgUrl, String storeName, String storeLocation, String closedDay, String openingHour, String storePhoneNumber, List<BaggageTypeInfoResponseDTO> baggageTypeInfoList) {
        return new StoreDetailResponseDTO(storeId, storeImgUrl, storeName, storeLocation, closedDay, openingHour, storePhoneNumber, baggageTypeInfoList);
    }

    public static StoreDetailResponseDTO getStoreDetailResponseDTO(final Store store, List<BaggageTypeInfoResponseDTO> baggageTypeInfoList){
        return StoreDetailResponseDTO.of(store.getStoreId(), store.getStoreImgUrl(), store.getStoreName(), store.getStoreLocation(), store.getClosedDay(), store.getOpeningHour(), store.getStorePhoneNumber(), baggageTypeInfoList);
    }
}
