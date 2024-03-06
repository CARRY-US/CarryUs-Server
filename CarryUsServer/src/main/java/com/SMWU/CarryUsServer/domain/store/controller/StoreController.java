package com.SMWU.CarryUsServer.domain.store.controller;

import com.SMWU.CarryUsServer.domain.reservation.controller.response.StoreReviewResponseListDTO;
import com.SMWU.CarryUsServer.domain.reservation.service.ReservationReviewService;
import com.SMWU.CarryUsServer.domain.store.controller.response.StoreDetailResponseDTO;
import com.SMWU.CarryUsServer.domain.store.service.StoreService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.SMWU.CarryUsServer.domain.store.exception.StoreSuccessType.GET_STORE_DETAIL;
import static com.SMWU.CarryUsServer.domain.store.exception.StoreSuccessType.GET_STORE_REVIEW_LIST;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final ReservationReviewService reservationReviewService;
    private final StoreService storeService;

    @GetMapping("/{storeId}/reviews")
    public ResponseEntity<SuccessResponse<StoreReviewResponseListDTO>> getStoreReviewList(@PathVariable final long storeId) {
        StoreReviewResponseListDTO responseDTO = reservationReviewService.getStoreReviewList(storeId);
        return ResponseEntity.ok().body(SuccessResponse.of(GET_STORE_REVIEW_LIST, responseDTO));
    }

    @GetMapping("/{storeId}/detail")
    public ResponseEntity<SuccessResponse<StoreDetailResponseDTO>> getStoreDetail(@PathVariable final long storeId) {
        StoreDetailResponseDTO responseDTO = storeService.getStoreDetail(storeId);
        return ResponseEntity.ok().body(SuccessResponse.of(GET_STORE_DETAIL, responseDTO));
    }
}
