package com.SMWU.CarryUsServer.domain.reservation.entity;

import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVATION_BAGGAGES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationBaggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationBaggageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_baggage_id")
    private StoreBaggage storeBaggage;

    private int reservationBaggageCount;

    public String getReservationBaggageInfo(){
        return storeBaggage.getBaggageType().getMessage()+" "+reservationBaggageCount+"개";
    }

    @Builder
    public ReservationBaggage(Reservation reservation, StoreBaggage storeBaggage, int reservationBaggageCount) {
        this.reservation = reservation;
        this.storeBaggage = storeBaggage;
        this.reservationBaggageCount = reservationBaggageCount;
    }
}
