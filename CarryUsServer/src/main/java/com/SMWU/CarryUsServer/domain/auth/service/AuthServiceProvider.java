package com.SMWU.CarryUsServer.domain.auth.service;

import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class AuthServiceProvider {
    private static final ConcurrentHashMap<PlatformType, AuthService> authSerivceMap = new ConcurrentHashMap<PlatformType, AuthService>();

    private final KakaoAuthService kakaoAuthService;

    @PostConstruct
    void initialAuthServiceMap() {
        authSerivceMap.put(PlatformType.KAKAO, kakaoAuthService);
    }

    public AuthService getAuthService(PlatformType platformType) {
        return authSerivceMap.get(platformType);
    }
}
