package com.SMWU.CarryUsServer.domain.store.controller;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.reservation.controller.request.ReservationCreateRequestDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.ReservationIdResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.StoreReviewResponseListDTO;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationCreateService;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationReviewService;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationService;
import com.SMWU.CarryUsServer.domain.store.controller.response.ReservationAvailableTimeResponseDTO;
import com.SMWU.CarryUsServer.domain.store.controller.response.StoreDetailResponseDTO;
import com.SMWU.CarryUsServer.domain.store.service.StoreService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import com.SMWU.CarryUsServer.global.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReservationSuccessType.RESERVATION_CREATE_SUCCESS;
import static com.SMWU.CarryUsServer.domain.store.exception.StoreSuccessType.*;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final ReservationReviewService reservationReviewService;
    private final StoreService storeService;
    private final ReservationCreateService reservationCreateService;
    private final ReservationService reservationService;

    @GetMapping("/{storeId}/reviews")
    public ResponseEntity<SuccessResponse<StoreReviewResponseListDTO>> getStoreReviewList(@PathVariable final long storeId) {
        final StoreReviewResponseListDTO responseDTO = reservationReviewService.getStoreReviewList(storeId);
        return ResponseEntity.ok().body(SuccessResponse.of(GET_STORE_REVIEW_LIST, responseDTO));
    }

    @GetMapping("/{storeId}/detail")
    public ResponseEntity<SuccessResponse<StoreDetailResponseDTO>> getStoreDetail(@PathVariable final long storeId) {
        final StoreDetailResponseDTO responseDTO = storeService.getStoreDetail(storeId);
        return ResponseEntity.ok().body(SuccessResponse.of(GET_STORE_DETAIL, responseDTO));
    }

    @PostMapping("/{storeId}/reservation")
    public ResponseEntity<SuccessResponse<ReservationIdResponseDTO>> createReservation(@PathVariable final long storeId, @RequestBody final ReservationCreateRequestDTO request, @AuthMember final Member member) {
        final ReservationIdResponseDTO response = reservationCreateService.createReservation(request, storeId, member);
        URI resourceUri = URI.create("/reservation/"+response.reservationId());
        return ResponseEntity.created(resourceUri).body(SuccessResponse.of(RESERVATION_CREATE_SUCCESS, response));
    }

    @GetMapping("/{storeId}/reservation/time/info")
    public ResponseEntity<SuccessResponse<ReservationAvailableTimeResponseDTO>> getReservationAvailableTime(
            @PathVariable final long storeId, @RequestParam final String date, @RequestParam final int extraSmallCount,
            @RequestParam final int smallCount, @RequestParam final int largeCount, @RequestParam final int extraLargeCount){
        final ReservationAvailableTimeResponseDTO response = reservationService.getReservationAvailableTime(storeId, date, extraSmallCount, smallCount, largeCount, extraLargeCount);
        return ResponseEntity.ok().body(SuccessResponse.of(GET_STORE_RESERVATION_AVAILABLE_TIME, response));
    }
}
