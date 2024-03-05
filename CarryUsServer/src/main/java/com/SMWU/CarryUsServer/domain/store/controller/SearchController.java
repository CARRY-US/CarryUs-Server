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

import static com.SMWU.CarryUsServer.domain.store.exception.StoreSuccessType.GET_STORE_LIST_BY_SEARCH;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final StoreService storeService;

    @GetMapping("")
    public ResponseEntity<SuccessResponse<List<StoreResponseDTO>>> getStoreListByCity(@RequestParam final String word){
        List<StoreResponseDTO> responseDTO = storeService.getStoreListByCity(word);
        return ResponseEntity.ok(SuccessResponse.of(GET_STORE_LIST_BY_SEARCH, responseDTO));
    }
}
