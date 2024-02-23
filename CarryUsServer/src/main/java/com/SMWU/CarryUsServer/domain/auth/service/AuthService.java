package com.SMWU.CarryUsServer.domain.auth.service;

import com.SMWU.CarryUsServer.domain.auth.controller.dto.request.MemberRequestDTO;
import com.SMWU.CarryUsServer.domain.auth.service.vo.MemberSignUpVO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import com.SMWU.CarryUsServer.domain.member.entity.enums.Role;
import com.SMWU.CarryUsServer.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public abstract class AuthService {
    private final MemberRepository memberRepository;

    @Transactional
    public abstract MemberSignUpVO saveMemberOrLogin(final String platformType, final MemberRequestDTO request);

    protected Member getMember(final PlatformType platformType, final String platformId) {
        return memberRepository.findByPlatformTypeAndPlatformId(platformType, platformId)
                .orElse(null);
    }

    protected Member saveUser(final MemberRequestDTO request, final String platformId, final String nickname, final String phoneNumber, final String profileImg) {
        Member newMember = createSocialMember(platformId, nickname, PlatformType.of(request.platformType()), Role.of(request.role()), phoneNumber, profileImg);
        return memberRepository.saveAndFlush(newMember);
    }

    private static Member createSocialMember(final String platformId, final String nickname, final PlatformType platformType, final Role role,
                                             final String phoneNumber, final String profileImg) {
        return Member.builder()
                .platformId(platformId)
                .nickname(nickname)
                .platformType(platformType)
                .role(role)
                .phoneNumber(phoneNumber)
                .profileImg(profileImg)
                .build();
    }
}
