package com.SMWU.CarryUsServer.domain.member.controller.response;

import com.SMWU.CarryUsServer.domain.member.entity.Member;

public record MemberProfileResponseDTO(
        String memberName,
        String memberProfileImg) {

    public static MemberProfileResponseDTO of(final Member member) {
        return new MemberProfileResponseDTO(member.getName(), member.getProfileImg());
    }
}
