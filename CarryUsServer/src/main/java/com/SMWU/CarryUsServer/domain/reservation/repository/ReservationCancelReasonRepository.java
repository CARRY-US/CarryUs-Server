package com.SMWU.CarryUsServer.domain.reservation.repository;

import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationCancelReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationCancelReasonRepository extends JpaRepository<ReservationCancelReason, Long>{
}
