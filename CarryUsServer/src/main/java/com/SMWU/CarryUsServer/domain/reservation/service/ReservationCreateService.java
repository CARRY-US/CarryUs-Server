package com.SMWU.CarryUsServer.domain.reservation.service;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReservationCreateRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReservationIdResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationBaggage;
import com.SMWU.CarryUsServer.domain.reservation.entity.enums.ReservationType;
import com.SMWU.CarryUsServer.domain.reservation.exception.ReservationException;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationBaggageRepository;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationRepository;
import com.SMWU.CarryUsServer.domain.store.entity.Store;
import com.SMWU.CarryUsServer.domain.store.entity.StoreBaggage;
import com.SMWU.CarryUsServer.domain.store.entity.enums.BaggageType;
import com.SMWU.CarryUsServer.domain.store.exception.StoreException;
import com.SMWU.CarryUsServer.domain.store.repository.StoreBaggageRepository;
import com.SMWU.CarryUsServer.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationExceptionType.NOT_VALIDATE_RESERVATION_BAGGAGE;
import static com.SMWU.CarryUsServer.domain.store.exception.StoreExceptionType.NOT_FOUND_STORE_BAGGAGE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReservationCreateService {
    private static final ReservationType RESERVATION_INIT_STATUS = ReservationType.WAITING;
    private final ReservationRepository reservationRepository;
    private final ReservationBaggageRepository reservationBaggageRepository;
    private final StoreBaggageRepository storeBaggageRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReservationIdResponseDTO createReservation(final ReservationCreateRequestDTO request, final Long storeId, final Member member) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreException(NOT_FOUND_STORE_BAGGAGE));
        ConcurrentHashMap<BaggageType, Integer> baggageCountMap = BaggageType.createBaggageCountMap(request.extraSmallCount(), request.smallCount(), request.largeCount(), request.extraLargeCount());
        Reservation reservation = createReservationEntity(request, store, member);
        reservationRepository.save(reservation);
        validateAndCreateReservationBaggage(baggageCountMap, store, request.reservationStartTime(), request.reservationEndTime(), reservation);
        return ReservationIdResponseDTO.of(reservation.getReservationId());
    }

    private Reservation createReservationEntity(final ReservationCreateRequestDTO request, final Store store, final Member member) {
        return Reservation.builder()
                .client(member)
                .store(store)
                .clientReservationPhoneNumber(request.memberPhoneNumber())
                .reservationType(RESERVATION_INIT_STATUS)
                .clientRequestContent(request.memberRequestContent())
                .reservationStartTime(request.reservationStartTime())
                .reservationEndTime(request.reservationEndTime())
                .build();
    }

    private void validateAndCreateReservationBaggage(final ConcurrentHashMap<BaggageType, Integer> baggageCount, final Store store, final LocalDateTime reservationStartTime, final LocalDateTime reservationEndTime, final Reservation reservation) {
        int payment = 0;
        int count = 0;
        StoreBaggage storeBaggage = null;

        for (BaggageType baggageType : BaggageType.values()) {
            for (LocalDateTime time = reservationStartTime; time.isBefore(reservationEndTime); time = time.plusHours(1)) {
                count = baggageCount.get(baggageType);
                storeBaggage = storeBaggageRepository.findByStoreAndBaggageType(store, baggageType)
                        .orElseThrow(() -> new StoreException(NOT_FOUND_STORE_BAGGAGE));
                int reservedCount = reservationBaggageRepository.findAllByStoreBaggageWithTime(storeBaggage, time)
                        .stream()
                        .mapToInt(ReservationBaggage::getReservationBaggageCount)
                        .sum();
                if (count + reservedCount > storeBaggage.getBaggageCount()) {
                    throw new ReservationException(NOT_VALIDATE_RESERVATION_BAGGAGE);
                }
            }
            createReservationBaggage(count, storeBaggage, reservation);
            payment += Objects.requireNonNull(storeBaggage).getBaggagePrice() * count;
        }
        reservation.updatePayment(payment);
    }

    private void createReservationBaggage(final int count, final StoreBaggage storeBaggage, final Reservation reservation) {
        if (count > 0) {
            ReservationBaggage reservationBaggage = ReservationBaggage.builder()
                    .reservation(reservation)
                    .storeBaggage(storeBaggage)
                    .reservationBaggageCount(count)
                    .build();
            reservationBaggageRepository.save(reservationBaggage);
        }
    }
}
