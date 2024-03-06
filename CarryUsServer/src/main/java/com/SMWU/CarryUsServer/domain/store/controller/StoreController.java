package com.SMWU.CarryUsServer.domain.store.controller;

import com.SMWU.CarryUsServer.domain.reservation.controller.response.StoreReviewResponseListDTO;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationReviewService;
import com.SMWU.CarryUsServer.domain.store.service.StoreService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.SMWU.CarryUsServer.domain.store.exception.StoreSuccessType.GET_STORE_REVIEW_LIST;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final ReservationReviewService reservationReviewService;

    @GetMapping("/{storeId}/reviews")
    public ResponseEntity<SuccessResponse<StoreReviewResponseListDTO>> getStoreReviewList(@PathVariable final long storeId) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_STORE_REVIEW_LIST, reservationReviewService.getStoreReviewList(storeId)));
    }
}
