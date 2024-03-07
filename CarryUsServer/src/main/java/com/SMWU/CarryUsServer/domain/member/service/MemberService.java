package com.SMWU.CarryUsServer.domain.member.service;

import com.SMWU.CarryUsServer.domain.member.controller.response.MemberProfileResponseDTO;
import com.SMWU.CarryUsServer.domain.reservation.controller.response.MemberReservationDefaultInfoResponseDTO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.SMWU.CarryUsServer.domain.reservation.controller.response.MemberReservationDefaultInfoResponseDTO.getMemberReservationDefaultInfoResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    public MemberProfileResponseDTO getMemberProfile(final Member member) {
        return MemberProfileResponseDTO.of(member.getName(), member.getProfileImg());
    }

    public MemberReservationDefaultInfoResponseDTO getMemberReservationDefaultInfo(final Member member) {
        return getMemberReservationDefaultInfoResponseDTO(member);
    }
}