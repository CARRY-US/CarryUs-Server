package com.SMWU.CarryUsServer.domain.reservation.entity;

import com.SMWU.CarryUsServer.global.entity.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVATIONS_REVIEWS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationReview extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationReviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private double reviewRating;

    private String reviewContent;
}
