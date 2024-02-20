package com.SMWU.CarryUsServer.domain.store.entity;

import com.SMWU.CarryUsServer.domain.store.entity.enums.BaggageType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STORE_BAGGAGES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoreBaggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeBaggageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private int baggagePrice;

    @Enumerated(EnumType.STRING)
    private BaggageType baggageType;

    private int baggageCount;
}
