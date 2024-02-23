package com.SMWU.CarryUsServer.domain.member.entity.enums;

import com.SMWU.CarryUsServer.domain.auth.exception.AuthException;

import java.util.Arrays;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.INVALID_MEMBER_ROLE;

public enum Role {
    TRAVELER, STORE_OWNER;
    public static Role of(String role) {
        return Arrays.stream(Role.values()).filter(type -> type.toString().equals(role)).findAny()
                .orElseThrow(() -> new AuthException(INVALID_MEMBER_ROLE));
    }
}
