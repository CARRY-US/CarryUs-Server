package com.SMWU.CarryUsServer.domain.auth.controller.dto.response;

public record MemberReissueResponseDTO(Long memberId, String accessToken, String refreshToken) {
    public static MemberReissueResponseDTO of(Long memberId, String accessToken, String refreshToken) {
        return new MemberReissueResponseDTO(memberId, accessToken, refreshToken);
    }
}
