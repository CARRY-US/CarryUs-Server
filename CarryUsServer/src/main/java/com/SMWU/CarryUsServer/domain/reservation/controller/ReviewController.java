package com.SMWU.CarryUsServer.domain.reservation.controller;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReviewUpdateRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReservationStoreInfoResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReviewIdResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReviewResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationReviewService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import com.SMWU.CarryUsServer.global.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewSuccessType.*;

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

    @PatchMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<ReviewIdResponseDTO>> updateReview(@PathVariable("reviewId") final Long reviewId, @RequestBody final ReviewUpdateRequestDTO requestDTO, @AuthMember final Member member) {
        final ReviewIdResponseDTO responseDTO = reservationReviewService.updateReview(reviewId, requestDTO, member);
        return ResponseEntity.ok(SuccessResponse.of(REVIEW_UPDATE_SUCCESS, responseDTO));
    }
}