package com.SMWU.CarryUsServer.domain.reservation.service;

import com.SMWU.CarryUsServer.domain.member.controller.response.MemberReviewResponseDTO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReviewCreateRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReviewUpdateRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.*;
import com.SMWU.CarryUsServer.domain.reservation.entity.Reservation;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;
import com.SMWU.CarryUsServer.domain.reservation.entity.enums.ReservationType;
import com.SMWU.CarryUsServer.domain.reservation.exception.ReservationException;
import com.SMWU.CarryUsServer.domain.reservation.exception.ReviewException;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationRepository;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationExceptionType.NOT_COMPLETED_RESERVATION;
import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationExceptionType.NOT_FOUND_RESERVATION;
import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewExceptiontype.NOT_FOUND_REVIEW;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationReviewService {
    private final ReservationReviewRepository reservationReviewRepository;
    private final ReservationRepository reservationRepository;

    public List<MemberReviewResponseDTO> getMemberReviewList(final Member member) {
        final List<ReservationReview> reservationReviewList = reservationReviewRepository.findAllByClient(member);
        return getMemberReviewResponseDTOList(reservationReviewList);
    }

    private List<MemberReviewResponseDTO> getMemberReviewResponseDTOList(final List<ReservationReview> reservationReviewList){
        List<MemberReviewResponseDTO> memberReviewListResponseDTO = new ArrayList<>();
        for (ReservationReview reservationReview : reservationReviewList) {
            memberReviewListResponseDTO.add(MemberReviewResponseDTO.of(reservationReview, reservationReview.getReservation().getStore().getStoreName()));
        }
        return memberReviewListResponseDTO;
    }

    public StoreReviewResponseListDTO getStoreReviewList(final Long storeId) {
        final List<ReservationReview> reservationReviewList = reservationReviewRepository.getReservationReviewByStore(storeId);
        final List<StoreReviewResponseDTO> reviewList = toStoreReviewList(reservationReviewList);
        final double reviewRatingAverage = reservationReviewRepository.getStoreRating(storeId).orElse(0.0);
        return StoreReviewResponseListDTO.of(reviewRatingAverage, reviewList);
    }

    private List<StoreReviewResponseDTO> toStoreReviewList(final List<ReservationReview> reservationReviewList) {
        List<StoreReviewResponseDTO> storeReviewListResponseDTO = new ArrayList<>();
        for (ReservationReview reservationReview : reservationReviewList) {
            storeReviewListResponseDTO.add(StoreReviewResponseDTO.of(reservationReview));
        }
        return storeReviewListResponseDTO;
    }

    public ReviewResponseDTO getReviewDetail(final Long reviewId, final Member member) {
        final ReservationReview reservationReview = reservationReviewRepository.findByReservationReviewIdAndReservationClient(reviewId, member)
                .orElseThrow(() -> new ReviewException(NOT_FOUND_REVIEW));
        return ReviewResponseDTO.of(reservationReview);
    }

    public ReservationStoreInfoResponseDTO getReservationStoreInfo(final Long reviewId, final Member member){
        final Reservation reservation = reservationRepository.findReservationWithReservationStore(reviewId, member)
                .orElseThrow(() -> new ReservationException(NOT_FOUND_RESERVATION));
        return ReservationStoreInfoResponseDTO.of(reservation.getStore(), reservation.getReservationInfo());
    }

    public List<ReservationFilteredByStatusResponseDTO> getMemberReservationByReservationStatus(final String status, final Member member){
        final ReservationType reservationType = ReservationType.valueOf(status);
        final List<Reservation> reservationList = reservationRepository.findAllByReservationTypeAndClientOrderByCreatedAtDesc(reservationType, member);
        return getReservationStoreInfoList(reservationList);
    }

    private List<ReservationFilteredByStatusResponseDTO> getReservationStoreInfoList(final List<Reservation> reservationList){
        List<ReservationFilteredByStatusResponseDTO> responseDTO = new ArrayList<>();
        boolean isReviewExist = false;
        for (Reservation reservation : reservationList) {
            boolean reservationTypeISCompleted = reservation.getReservationType().equals(ReservationType.COMPLETED);
            boolean reservationHasReview = reservationReviewRepository.existsByReservation(reservation);
            isReviewExist = reservationTypeISCompleted && reservationHasReview;
            responseDTO.add(ReservationFilteredByStatusResponseDTO.of(reservation.getStore(), reservation, isReviewExist));
        }
        return responseDTO;
    }

    @Transactional
    public ReviewIdResponseDTO createReview(final Long reservationId, final ReviewCreateRequestDTO requestDTO, final Member member) {
        final Reservation reservation = reservationRepository.findByReservationIdAndClient(reservationId, member)
                .orElseThrow(() -> new ReservationException(NOT_FOUND_RESERVATION));
        if(reservation.getReservationType().equals(ReservationType.COMPLETED)) {
            final ReservationReview reservationReview = createReservationReview(reservation, requestDTO);
            reservationReviewRepository.save(reservationReview);
            return ReviewIdResponseDTO.of(reservationReview.getReservationReviewId());
        }
        throw new ReservationException(NOT_COMPLETED_RESERVATION);
    }

    private ReservationReview createReservationReview(final Reservation reservation, final ReviewCreateRequestDTO requestDTO) {
        return ReservationReview.builder()
                .reservation(reservation)
                .reviewContent(requestDTO.reviewContent())
                .reviewRating(requestDTO.reviewRating())
                .build();
    }

    @Transactional
    public ReviewIdResponseDTO updateReview(final Long reviewId, final ReviewUpdateRequestDTO request, final Member member) {
        final ReservationReview reservationReview = reservationReviewRepository.findByReservationReviewIdAndReservationClient(reviewId, member)
                .orElseThrow(() -> new ReviewException(NOT_FOUND_REVIEW));
        reservationReview.updateReview(request.reviewRating(), request.reviewContent());
        return ReviewIdResponseDTO.of(reservationReview.getReservationReviewId());
    }
}
