package com.SMWU.CarryUsServer.domain.reservation.controller;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReviewCreateRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReviewIdResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationReviewService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import com.SMWU.CarryUsServer.global.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewSuccessType.REVIEW_CREATE_SUCCESS;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationReviewService reservationReviewService;

    @PostMapping("/{reservationId}/review")
    public ResponseEntity<SuccessResponse<ReviewIdResponseDTO>> createReview(@PathVariable("reservationId") final Long reservationId, @RequestBody final ReviewCreateRequestDTO requestDTO, @AuthMember final Member member) {
        final ReviewIdResponseDTO responseDTO = reservationReviewService.createReview(reservationId, requestDTO, member);
        return ResponseEntity.ok(SuccessResponse.of(REVIEW_CREATE_SUCCESS, responseDTO));
    }
}
