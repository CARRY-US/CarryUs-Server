package com.SMWU.CarryUsServer.domain.reservation.service;

import com.SMWU.CarryUsServer.domain.member.controller.response.MemberReviewResponseDTO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.entity.ReservationReview;
import com.SMWU.CarryUsServer.domain.reservation.repository.ReservationReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationReviewService {
    private final ReservationReviewRepository reservationReviewRepository;

    public List<MemberReviewResponseDTO> getMemberReviewList(final Member member) {
        final List<ReservationReview> reservationReviews = reservationReviewRepository.findAllByClient(member);
        List<MemberReviewResponseDTO> memberReviewListResponseDTO = new ArrayList<>();
        for (ReservationReview reservationReview : reservationReviews) {
            memberReviewListResponseDTO.add(MemberReviewResponseDTO.of(
                    reservationReview.getReservationReviewId(),
                    reservationReview.getReservation().getStore().getStoreName(),
                    reservationReview.getReviewRating(),
                    reservationReview.getReviewContent()));
        }
        return memberReviewListResponseDTO;
    }

}
