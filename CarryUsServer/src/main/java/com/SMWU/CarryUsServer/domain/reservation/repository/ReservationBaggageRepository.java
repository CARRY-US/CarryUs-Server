package com.SMWU.CarryUsServer.domain.reservation.repository;

import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationBaggage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationBaggageRepository extends JpaRepository<ReservationBaggage, Long> {
    List<ReservationBaggage> findAllByReservation(Reservation reservation);
}
