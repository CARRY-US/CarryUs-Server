package com.SMWU.CarryUsServer.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVATIONS_CANCEL_REASONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationCancelReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationCancelReasonId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String cancelReason;
}
