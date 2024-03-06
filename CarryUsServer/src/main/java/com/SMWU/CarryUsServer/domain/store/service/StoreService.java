package com.SMWU.CarryUsServer.domain.store.service;

import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationReviewRepository;
import com.SMWU.CarryUsServer.domain.store.controller.response.BaggageTypeInfoResponseDTO;
import com.SMWU.CarryUsServer.domain.store.controller.response.StoreDetailResponseDTO;
import com.SMWU.CarryUsServer.domain.store.controller.response.StoreResponseDTO;
import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;
import com.SMWU.CarryUsServer.domain.store.repository.StoreBaggageRepository;
import com.SMWU.CarryUsServer.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.SMWU.CarryUsServer.domain.store.controller.response.BaggageTypeInfoResponseDTO.getBaggageTypeInfoResponseDTO;
import static com.SMWU.CarryUsServer.domain.store.controller.response.StoreResponseDTO.toStoreResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    private final StoreRepository storeRepository;
    private final ReservationReviewRepository reviewRepository;
    private final StoreBaggageRepository storeBaggageRepository;
    public static final double EMPTY_RATING = 0.0;

    public List<StoreResponseDTO> getStoreListByMemberLocation(final double xMin, final double xMax, final double yMin, final double yMax) {
        final List<Store> storeList = storeRepository.findAllByLatitudeBetweenAndLongitudeBetweenOrderByStoreId(xMin, xMax, yMin, yMax);
        return getStoreResponseDTOList(storeList);
    }

    public List<StoreResponseDTO> getStoreListByLocation(final double x, final double y){
        final List<Store> storeList = storeRepository.findAllByLatitudeAndLongitude(x, y);
        return getStoreResponseDTOList(storeList);
    }

    public List<StoreResponseDTO> getStoreListByCity(final String city){
        final List<Store> storeList = storeRepository.findAllByCity(city);
        return getStoreResponseDTOList(storeList);
    }

    private List<StoreResponseDTO> getStoreResponseDTOList(List<Store> storeList){
        List<StoreResponseDTO> responseDTOList = new ArrayList<>();
        for(Store store : storeList) {
            final int reviewCount = reviewRepository.getReviewCount(store.getStoreId());
            final double reviewRating = reviewRepository.getStoreRating(store.getStoreId()).orElse(EMPTY_RATING);
            responseDTOList.add(toStoreResponseDTO(store, reviewCount, reviewRating));
        }
        return responseDTOList;
    }

    public StoreDetailResponseDTO getStoreDetail(final long storeId) {
        final Store store = storeRepository.findById(storeId).orElseThrow();
        final List<BaggageTypeInfoResponseDTO> baggageTypeInfoList = getBaggageTypeInfoList(store);
        return StoreDetailResponseDTO.getStoreDetailResponseDTO(store, baggageTypeInfoList);
    }

    private List<BaggageTypeInfoResponseDTO> getBaggageTypeInfoList(final Store store){
        List<BaggageTypeInfoResponseDTO> baggageTypeInfoList = new ArrayList<>();
        final List<StoreBaggage> storeBaggageList = storeBaggageRepository.findAllByStoreOrderByStoreBaggageIdAsc(store);
        for(StoreBaggage storeBaggage : storeBaggageList){
            baggageTypeInfoList.add(getBaggageTypeInfoResponseDTO(storeBaggage));
        }
        return baggageTypeInfoList;
    }
}
