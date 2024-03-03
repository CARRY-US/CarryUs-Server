package com.SMWU.CarryUsServer.domain.reservation.controller;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReservationStoreInfoResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReviewResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationReviewService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import com.SMWU.CarryUsServer.global.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewSuccessType.REVIEW_GET_SUCCESS;
import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewSuccessType.REVIEW_STORE_INFO_GET_SUCCESS;

@RequestMapping("/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReservationReviewService reservationReviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<ReviewResponseDTO>> getReviewDetail(@PathVariable("reviewId") final Long reviewId, @AuthMember final Member member) {
        final ReviewResponseDTO responseDTO = reservationReviewService.getReviewDetail(reviewId, member);
        return ResponseEntity.ok(SuccessResponse.of(REVIEW_GET_SUCCESS, responseDTO));
    }

    @GetMapping("/{reviewId}/store/info")
    public ResponseEntity<SuccessResponse<ReservationStoreInfoResponseDTO>> getReservationStoreInfo(@PathVariable("reviewId") final Long reviewId, @AuthMember final Member member) {
        final ReservationStoreInfoResponseDTO responseDTO = reservationReviewService.getReservationStoreInfo(reviewId, member);
        return ResponseEntity.ok(SuccessResponse.of(REVIEW_STORE_INFO_GET_SUCCESS, responseDTO));
    }
}