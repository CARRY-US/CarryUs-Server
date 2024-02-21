package com.SMWU.CarryUsServer.domain.auth.service.vo;

import com.SMWU.CarryUsServer.domain.auth.controller.dto.enums.AuthType;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import com.SMWU.CarryUsServer.domain.member.entity.enums.Role;
import lombok.Builder;

@Builder
public record MemberSignUpVO(Long memberId, String platformId, String nickName, PlatformType platformType, Role role,
                             String birthYear, String gender, String phoneNumber, String profileImg, AuthType authType) {

    public static MemberSignUpVO of(Member member, PlatformType platformType, Role role, AuthType authtype) {
        return MemberSignUpVO.builder()
                .memberId(member.getMemberId())
                .platformId(member.getPlatformId())
                .nickName(member.getNickname())
                .platformType(platformType)
                .role(role)
                .phoneNumber(member.getPhoneNumber())
                .profileImg(member.getProfileImg())
                .authType(authtype)
                .build();
    }
}
