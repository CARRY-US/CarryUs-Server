package com.SMWU.CarryUsServer.domain.store.exception;

import com.SMWU.CarryUsServer.global.exception.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum StoreSuccessType implements SuccessType {
    GET_STORE_LIST_BY_MEMBER_LOCATION(HttpStatus.OK, "사용자 위치 기반 가게 목록 조회에 성공하였습니다."),
    GET_STORE_LIST_BY_LOCATION(HttpStatus.OK, "특정 위치에 따른 가게 목록 조회에 성공하였습니다"),

    GET_STORE_LIST_BY_SEARCH(HttpStatus.OK, "검색에 따른 가게 목록 조회에 성공하였습니다");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
