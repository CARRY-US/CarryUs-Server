package com.SMWU.CarryUsServer.domain.store.service;

import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationReviewRepository;
import com.SMWU.CarryUsServer.domain.store.controller.response.StoreResponseDTO;
import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    private final StoreRepository storeRepository;
    private final ReservationReviewRepository reviewRepository;
    public static final double EMPTY_RATING = 0.0;

    public List<StoreResponseDTO> getStoreListByMemberLocation(final double xMin, final double xMax, final double yMin, final double yMax) {
        final List<Store> storeList = storeRepository.findByLatitudeBetweenAndLongitudeBetweenOrderByStoreId(xMin, xMax, yMin, yMax);
        List<StoreResponseDTO> responseDTOList = new ArrayList<>();
        for(Store store : storeList) {
            final int reviewCount = reviewRepository.getReviewCount(store.getStoreId());
        final double reviewRating = reviewRepository.getStoreRating(store.getStoreId()).orElse(EMPTY_RATING);
            responseDTOList.add(toResponseDTO(store, reviewCount, reviewRating));
        }
        return responseDTOList;
    }

    private StoreResponseDTO toResponseDTO(final Store store,final int reviewCount, final double reviewRatingAverage) {
        return StoreResponseDTO.of(store.getStoreId(), store.getStoreImgUrl(), store.getStoreName(), store.getStoreLocation(), reviewCount ,reviewRatingAverage , store.getLatitude(), store.getLongitude());
    }
}
