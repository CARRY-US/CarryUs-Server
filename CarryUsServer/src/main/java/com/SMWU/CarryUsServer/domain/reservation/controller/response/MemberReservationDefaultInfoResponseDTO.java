package com.SMWU.CarryUsServer.domain.reservation.controller.response;

import com.SMWU.CarryUsServer.domain.member.entity.Member;

public record MemberReservationDefaultInfoResponseDTO(
        String memberName,
        String memberPhoneNumber
) {
    public static MemberReservationDefaultInfoResponseDTO of(final Member member) {
        return new MemberReservationDefaultInfoResponseDTO(member.getName(), member.getPhoneNumber());
    }
}
