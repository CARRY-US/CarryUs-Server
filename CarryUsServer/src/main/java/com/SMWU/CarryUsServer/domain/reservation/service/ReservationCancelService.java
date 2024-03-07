package com.SMWU.CarryUsServer.domain.reservation.service;

import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReservationCancelRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReservationIdResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationCancelReason;
import com.SMWU.CarryUsServer.domain.reservation.exception.ReservationException;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationCancelReasonRepository;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationExceptionType.NOT_FOUND_RESERVATION;

@Service
@RequiredArgsConstructor
public class ReservationCancelService {
    private final ReservationCancelReasonRepository reservationCancelReasonRepository;
    private final ReservationRepository reservationRepository;

    public ReservationIdResponseDTO cancelReservation(final ReservationCancelRequestDTO request) {
        final Reservation reservation = reservationRepository.findById(request.reservationId()).orElseThrow(() -> new ReservationException(NOT_FOUND_RESERVATION));
        reservation.cancelReservation();
        final ReservationCancelReason reservationCancelReason = ReservationCancelReason.builder()
                .reservation(reservation)
                .cancelReason(request.cancelReason())
                .build();
        reservationCancelReasonRepository.save(reservationCancelReason);
        return ReservationIdResponseDTO.of(reservation.getReservationId());
    }
}
