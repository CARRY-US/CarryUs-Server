package com.SMWU.CarryUsServer.domain.reservation.repository;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationIdAndClient(Long id, Member client);

    @Query("SELECT r FROM Reservation r JOIN ReservationReview rr on r = rr.reservation JOIN FETCH r.store WHERE rr.reservationReviewId = :reviewId AND r.client = :client")
    Optional<Reservation> findReservationWithReservationStore(@Param("reviewId") Long reviewId, @Param("client") Member client);
}