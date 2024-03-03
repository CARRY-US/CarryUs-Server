package com.SMWU.CarryUsServer.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STORES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String storeName;

    private String openingHour;

    private String closedDay;

    private String storePhoneNumber;

    private String storeImgUrl;

    private String state;

    private String city;

    private String town;

    private String addressRest;

    private double latitude;

    private double longitude;

    public String getStoreLocation(){
        return state + " " + city + " " + town + " " + addressRest;
    }
}
