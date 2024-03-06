package com.SMWU.CarryUsServer.domain.store.repository;

import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreBaggageRepository extends JpaRepository<StoreBaggage, Long> {
    List<StoreBaggage> findAllByStoreOrderByStoreBaggageIdAsc(Store store);
}
