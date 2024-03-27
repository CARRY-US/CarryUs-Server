package com.SMWU.CarryUsServer.domain.reservation.controller;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.service.MemberService;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReservationCancelRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReviewCreateRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.*;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationCancelService;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationReviewService;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import com.SMWU.CarryUsServer.global.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationSuccessType.*;
import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewSuccessType.REVIEW_BY_STATUS_GET_SUCCESS;
import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewSuccessType.REVIEW_CREATE_SUCCESS;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationReviewService reservationReviewService;
    private final MemberService memberService;
    private final ReservationCancelService reservationCancelService;
    private final ReservationService reservationService;

    @PostMapping("/{reservationId}/review")
    public ResponseEntity<SuccessResponse<ReviewIdResponseDTO>> createReview(@PathVariable("reservationId") final Long reservationId, @RequestBody final ReviewCreateRequestDTO requestDTO, @AuthMember final Member member) {
        final ReviewIdResponseDTO responseDTO = reservationReviewService.createReview(reservationId, requestDTO, member);
        return ResponseEntity.ok(SuccessResponse.of(REVIEW_CREATE_SUCCESS, responseDTO));
    }

    @GetMapping("/my/info")
    public ResponseEntity<SuccessResponse<MemberReservationDefaultInfoResponseDTO>> getMemberReservationDefaultInfo(@AuthMember final Member member) {
        final MemberReservationDefaultInfoResponseDTO responseDTO = memberService.getMemberReservationDefaultInfo(member);
        return ResponseEntity.ok(SuccessResponse.of(RESERVATION_MEMBER_DEFAULT_INFO_GET_SUCCESS, responseDTO));
    }

    @PostMapping("")
    public ResponseEntity<SuccessResponse<ReservationIdResponseDTO>> cancelReservation(@RequestBody final ReservationCancelRequestDTO request) {
        final ReservationIdResponseDTO responseDTO = reservationCancelService.cancelReservation(request);
        return ResponseEntity.ok(SuccessResponse.of(RESERVATION_CANCEL_SUCCESS, responseDTO));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<SuccessResponse<ReservationDetailResponseDTO>> getReservationDetail(@PathVariable final Long reservationId, @AuthMember final Member member){
        final ReservationDetailResponseDTO responseDTO = reservationService.getReservationDetail(reservationId, member);
        return ResponseEntity.ok(SuccessResponse.of(RESERVATION_DETAIL_GET_SUCCESS, responseDTO));
    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse<List<ReservationFilteredByStatusResponseDTO>>> getMemberReservationByReservationStatus(@RequestParam final String status, @AuthMember final Member member){
        final List<ReservationFilteredByStatusResponseDTO> responseDTO = reservationReviewService.getMemberReservationByReservationStatus(status, member);
        return ResponseEntity.ok(SuccessResponse.of(REVIEW_BY_STATUS_GET_SUCCESS, responseDTO));
    }
}
