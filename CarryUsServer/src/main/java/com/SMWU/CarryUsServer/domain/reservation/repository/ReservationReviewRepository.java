package com.SMWU.CarryUsServer.domain.reservation.repository;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationReviewRepository extends JpaRepository<ReservationReview, Long>{
    @Query("SELECT rr FROM ReservationReview rr JOIN FETCH rr.reservation r JOIN FETCH r.store WHERE rr.reservation.client = :client ORDER BY rr.createdAt DESC")
    List<ReservationReview> findAllByClient(@Param("client") Member client);
}