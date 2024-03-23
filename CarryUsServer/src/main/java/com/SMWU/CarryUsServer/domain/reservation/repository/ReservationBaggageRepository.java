package com.SMWU.CarryUsServer.domain.reservation.repository;

import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationBaggage;
import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationBaggageRepository extends JpaRepository<ReservationBaggage, Long> {
    List<ReservationBaggage> findAllByReservation(Reservation reservation);

    @Query("select rb from ReservationBaggage rb where rb.storeBaggage = :storeBaggage and :time between rb.reservation.reservationStartTime and rb.reservation.reservationEndTime")
    List<ReservationBaggage> findAllByStoreBaggageWithTime(StoreBaggage storeBaggage, LocalDateTime time);
}
