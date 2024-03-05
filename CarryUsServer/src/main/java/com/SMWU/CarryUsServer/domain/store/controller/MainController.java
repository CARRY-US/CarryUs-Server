package com.SMWU.CarryUsServer.domain.store.controller;

import com.SMWU.CarryUsServer.domain.store.controller.response.StoreResponseDTO;
import com.SMWU.CarryUsServer.domain.store.service.StoreService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.SMWU.CarryUsServer.domain.store.exception.StoreSuccessType.GET_STORE_LIST_BY_MEMBER_LOCATION;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final StoreService storeService;

    @GetMapping("/location/stores")
    public ResponseEntity<SuccessResponse<List<StoreResponseDTO>>> getStoreListByMemberLocation(
            @RequestParam final double xMin,
            @RequestParam final double xMax,
            @RequestParam final double yMin,
            @RequestParam final double yMax){
        final List<StoreResponseDTO> responseDTO = storeService.getStoreListByMemberLocation(xMin, xMax, yMin, yMax);
        return ResponseEntity.ok(SuccessResponse.of(GET_STORE_LIST_BY_MEMBER_LOCATION, responseDTO));
    }

    @GetMapping("/stores")
    public ResponseEntity<SuccessResponse<List<StoreResponseDTO>>> getStoreListByLocation(
            @RequestParam final double x,
            @RequestParam final double y){
        final List<StoreResponseDTO> responseDTO = storeService.getStoreListByLocation(x, y);
        return ResponseEntity.ok(SuccessResponse.of(GET_STORE_LIST_BY_MEMBER_LOCATION, responseDTO));
    }
}
