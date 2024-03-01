package com.SMWU.CarryUsServer.domain.member.controller.response;

public record MemberProfileResponseDTO(String memberName, String memberProfileImg) {
    public static MemberProfileResponseDTO of(String memberName, String memberProfileImg) {
        return new MemberProfileResponseDTO(memberName, memberProfileImg);
    }
}
