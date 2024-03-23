package com.SMWU.CarryUsServer.domain.store.repository;

import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;
import com.SMWU.CarryUsServer.domain.store.entity.enums.BaggageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreBaggageRepository extends JpaRepository<StoreBaggage, Long> {
    List<StoreBaggage> findAllByStoreOrderByStoreBaggageIdAsc(Store store);
    Optional<StoreBaggage> findByStoreAndBaggageType(Store store, BaggageType baggageType);
}
