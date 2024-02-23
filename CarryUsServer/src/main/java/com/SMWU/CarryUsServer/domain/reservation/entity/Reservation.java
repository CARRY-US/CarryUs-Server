package com.SMWU.CarryUsServer.domain.reservation.entity;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.entity.enums.ReservationType;
import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.global.entity.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Member client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String clientReservationPhoneNumber;

    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    private String clientRequestContent;

    private int payment;

    private LocalDateTime reservationStartTime;

    private LocalDateTime reservationEndTime;
}
