package com.SMWU.CarryUsServer.domain.member.controller.response;

import com.SMWU.CarryUsServer.domain.member.entity.Member;

public record MemberReservationDefaultInfoResponseDTO(
        String memberName,
        String memberPhoneNumber
) {
    private static MemberReservationDefaultInfoResponseDTO of(final String memberName, final String memberPhoneNumber) {
        return new MemberReservationDefaultInfoResponseDTO(memberName, memberPhoneNumber);
    }

    public static MemberReservationDefaultInfoResponseDTO getMemberReservationDefaultInfoResponseDTO (final Member member) {
        return MemberReservationDefaultInfoResponseDTO.of(member.getName(), member.getPhoneNumber());
    }
}
