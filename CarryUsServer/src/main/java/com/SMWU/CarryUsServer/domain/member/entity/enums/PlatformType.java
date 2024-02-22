package com.SMWU.CarryUsServer.domain.member.entity.enums;

import com.SMWU.CarryUsServer.domain.auth.exception.AuthException;

import java.util.Arrays;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.INVALID_PLATFORM_TYPE;

public enum PlatformType {
    KAKAO, NAVER;

    public static PlatformType of(String platformType) {
        return Arrays.stream(PlatformType.values()).filter(type -> type.toString().equals(platformType)).findAny()
                .orElseThrow(() -> new AuthException(INVALID_PLATFORM_TYPE));
    }
}
