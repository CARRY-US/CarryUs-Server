package com.SMWU.CarryUsServer.domain.member.service;

import com.SMWU.CarryUsServer.domain.member.controller.response.MemberProfileResponseDTO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberProfileResponseDTO getMemberProfile(final Member member) {
        return MemberProfileResponseDTO.of(member.getName(), member.getProfileImg());
    }
}