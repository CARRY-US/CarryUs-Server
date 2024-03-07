package com.SMWU.CarryUsServer.domain.store.controller.response;

import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;

public record BaggageTypeInfoResponseDTO(
        String baggageType,
        int baggageCount,
        int baggagePrice) {
    public static BaggageTypeInfoResponseDTO of(StoreBaggage storeBaggage){
        return new BaggageTypeInfoResponseDTO(storeBaggage.getBaggageType().getMessage(), storeBaggage.getBaggageCount(), storeBaggage.getBaggagePrice());
    }
}
