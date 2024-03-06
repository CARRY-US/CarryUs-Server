package com.SMWU.CarryUsServer.domain.store.controller.response;

import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;

public record BaggageTypeInfoResponseDTO(
        String baggageType,
        int baggageCount,
        int baggagePrice) {
    public static BaggageTypeInfoResponseDTO of(String baggageType, int baggageCount, int baggagePrice) {
        return new BaggageTypeInfoResponseDTO(baggageType, baggageCount, baggagePrice);
    }

    public static BaggageTypeInfoResponseDTO getBaggageTypeInfoResponseDTO(StoreBaggage storeBaggage){
        return BaggageTypeInfoResponseDTO.of(storeBaggage.getBaggageType().getMessage(), storeBaggage.getBaggageCount(), storeBaggage.getBaggagePrice());
    }
}
