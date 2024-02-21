package com.SMWU.CarryUsServer.domain.auth.controller.dto;

import com.SMWU.CarryUsServer.domain.auth.controller.dto.enums.AuthType;
import com.SMWU.CarryUsServer.domain.member.entity.enums.Role;

public record MemberAuthResponseDTO(Long memberId, AuthType authType, Role role, String accessToken, String refreshToken) {
    public static MemberAuthResponseDTO of(Long memberId, AuthType authType, Role role, String accessToken, String refreshToken) {
        return new MemberAuthResponseDTO(memberId, authType, role, accessToken, refreshToken);
    }
}
