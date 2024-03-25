package com.SMWU.CarryUsServer.domain.reservation.service;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReservationDetailResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationBaggage;
import com.SMWU.CarryUsServer.domain.reservation.exception.ReservationException;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationBaggageRepository;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationRepository;
import com.SMWU.CarryUsServer.domain.store.controller.response.ReservationAvailableTimeResponseDTO;
import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;
import com.SMWU.CarryUsServer.domain.store.entity.enums.BaggageType;
import com.SMWU.CarryUsServer.domain.store.exception.StoreException;
import com.SMWU.CarryUsServer.domain.store.repository.StoreBaggageRepository;
import com.SMWU.CarryUsServer.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationExceptionType.NOT_FOUND_RESERVATION;
import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationExceptionType.NOT_VALIDATE_RESERVATION_BAGGAGE;
import static com.SMWU.CarryUsServer.domain.store.exception.StoreExceptionType.NOT_FOUND_STORE;
import static com.SMWU.CarryUsServer.domain.store.exception.StoreExceptionType.NOT_FOUND_STORE_BAGGAGE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationBaggageRepository reservationBaggageRepository;
    private final StoreRepository storeRepository;
    private final StoreBaggageRepository storeBaggageRepository;


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

    public ReservationAvailableTimeResponseDTO getReservationAvailableTime(final long storeId, final String date, final int extraSmallCount, final int smallCount, final int largeCount, final int extraLargeCount){
        ConcurrentHashMap<BaggageType, Integer> baggageCountMap = BaggageType.createBaggageCountMap(extraSmallCount, smallCount, largeCount, extraLargeCount);
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreException(NOT_FOUND_STORE));
        LocalDateTime reservationDate = LocalDateTime.parse(date + "T00:00:00");
        final List<Boolean> response = getAvailableTime(baggageCountMap, store, reservationDate);
        return ReservationAvailableTimeResponseDTO.of(response);
    }

    private List<Boolean> getAvailableTime(final ConcurrentHashMap<BaggageType, Integer> baggageCount, final Store store, final LocalDateTime reservationDate) {
        List<Boolean> availableTimeList = new ArrayList<>();
        LocalDateTime startTime = getStartTime(store, reservationDate);
        LocalDateTime endTime = getEndTime(store, reservationDate);

        for (int i = 0; i < 24; i++) {
            LocalDateTime currentTime = reservationDate.plusHours(i);
            if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
                availableTimeList.add(false);
                continue;
            }
            boolean available = isAvailableForBaggageType(baggageCount, store, currentTime);
            availableTimeList.add(available);
        }
        return availableTimeList;
    }

    private boolean isAvailableForBaggageType(final ConcurrentHashMap<BaggageType, Integer> baggageCount, final Store store, final LocalDateTime currentTime) {
        for (BaggageType baggageType : BaggageType.values()) {
            int count = baggageCount.get(baggageType);
            StoreBaggage storeBaggage = storeBaggageRepository.findByStoreAndBaggageType(store, baggageType)
                    .orElseThrow(() -> new StoreException(NOT_FOUND_STORE_BAGGAGE));
            int reservedCount = reservationBaggageRepository.findAllByStoreBaggageWithTime(storeBaggage, currentTime)
                    .stream()
                    .mapToInt(ReservationBaggage::getReservationBaggageCount)
                    .sum();
            if (count + reservedCount > storeBaggage.getBaggageCount()) {
                return false;
            }
        }
        return true;
    }

    private LocalDateTime getStartTime(final Store store, final LocalDateTime reservationDate){
        Pattern pattern = Pattern.compile("\\b\\d{2}:\\d{2}\\b");
        Matcher matcher = pattern.matcher(store.getOpeningHour());
        LocalTime startTime = null;
        if (matcher.find()) {
            String timeString = matcher.group();
            startTime = LocalTime.parse(timeString);
        }
        return LocalDateTime.of(reservationDate.toLocalDate(), Objects.requireNonNull(startTime));
    }

    private LocalDateTime getEndTime(final Store store, final LocalDateTime reservationDate){
        Pattern pattern = Pattern.compile("\\d{2}:\\d{2}(?!.*\\d{2}:\\d{2})");
        Matcher matcher = pattern.matcher(store.getOpeningHour());
        LocalTime endTime = null;
        if (matcher.find()) {
            String timeString = matcher.group();
            endTime = LocalTime.parse(timeString);
        }

        System.out.println("endTime: " + endTime);
        return LocalDateTime.of(reservationDate.toLocalDate(), Objects.requireNonNull(endTime));
    }
}
