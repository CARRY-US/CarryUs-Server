package com.SMWU.CarryUsServer.domain.store.repository;

import com.SMWU.CarryUsServer.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>{
    List<Store> findAllByLatitudeBetweenAndLongitudeBetweenOrderByStoreId(final double xMin, final double xMax, final double yMin, final double yMax);

    List<Store> findAllByLatitudeAndLongitude(final double x, final double y);

    List<Store> findAllByCity(final String city);
}
