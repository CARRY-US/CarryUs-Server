package com.SMWU.CarryUsServer.domain.reservation.service;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReservationDetailResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationBaggage;
import com.SMWU.CarryUsServer.domain.reservation.exception.ReservationException;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationBaggageRepository;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationRepository;
import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.domain.store.repository.StoreBaggageRepository;
import com.SMWU.CarryUsServer.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationExceptionType.NOT_FOUND_RESERVATION;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationBaggageRepository reservationBaggageRepository;


    public ReservationDetailResponseDTO getReservationDetail(final Long reservationId, final Member member){
        final Reservation reservation = reservationRepository.findByReservationIdAndClient(reservationId, member).orElseThrow(() -> new ReservationException(NOT_FOUND_RESERVATION));
        final Store store = reservation.getStore();
        final List<ReservationBaggage> reservationBaggageList = reservationBaggageRepository.findAllByReservation(reservation);
        final String reservationBaggageInfo = getReservationBaggageInfo(reservationBaggageList);
        final int reservationPayment = getReservationPayment(reservationBaggageList);
        return ReservationDetailResponseDTO.of(store, reservation, member, reservationBaggageInfo, reservationPayment);
    }

    private String getReservationBaggageInfo(final List<ReservationBaggage> reservationBaggageList){
        StringBuilder reservationBaggageInfo = new StringBuilder();
        for (ReservationBaggage rb : reservationBaggageList) {
            reservationBaggageInfo.append(rb.getReservationBaggageInfo()).append(", ");
        }
        if (!reservationBaggageInfo.isEmpty()) {
            reservationBaggageInfo.setLength(reservationBaggageInfo.length() - 2);
        }
        return reservationBaggageInfo.toString();
    }

    private int getReservationPayment(final List<ReservationBaggage> reservationBaggageList){
        int payment = 0;
        for(ReservationBaggage rb : reservationBaggageList){
            int price = rb.getStoreBaggage().getBaggagePrice();
            int count = rb.getReservationBaggageCount();
            payment += price*count;
        }
        return payment;
    }
}
