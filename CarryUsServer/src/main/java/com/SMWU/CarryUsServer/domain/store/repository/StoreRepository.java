package com.SMWU.CarryUsServer.domain.store.repository;

import com.SMWU.CarryUsServer.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>{
    List<Store> findByLatitudeBetweenAndLongitudeBetweenOrderByStoreId(double xMin, double xMax, double yMin, double yMax);
}
