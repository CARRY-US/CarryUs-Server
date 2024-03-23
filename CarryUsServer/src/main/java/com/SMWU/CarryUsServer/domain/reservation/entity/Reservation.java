package com.SMWU.CarryUsServer.domain.reservation.entity;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.entity.enums.ReservationType;
import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.global.entity.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getReservationInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm 예약");
        return reservationStartTime.format(formatter);
    }

    public void cancelReservation(){
        this.reservationType = ReservationType.CANCELED;
    }

    public void updatePayment(int payment){
        this.payment = payment;
    }

    @Builder
    public Reservation(Member client, Store store, String clientReservationPhoneNumber, ReservationType reservationType, String clientRequestContent, int payment, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        this.client = client;
        this.store = store;
        this.clientReservationPhoneNumber = clientReservationPhoneNumber;
        this.reservationType = reservationType;
        this.clientRequestContent = clientRequestContent;
        this.payment = payment;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
    }
}
