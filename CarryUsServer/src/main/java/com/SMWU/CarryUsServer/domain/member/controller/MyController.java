package com.SMWU.CarryUsServer.domain.member.controller;

import com.SMWU.CarryUsServer.domain.member.controller.response.MemberProfileResponseDTO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.service.MemberService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import com.SMWU.CarryUsServer.global.security.AuthMember;
import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.SMWU.CarryUsServer.domain.member.exception.MemberSuccessType.MEMBER_PROFILE_GET_SUCCESS;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse<MemberProfileResponseDTO>> getMemberProfile(@AuthMember final Member member) {
        final MemberProfileResponseDTO memberProfileResponseDTO = memberService.getMemberProfile(member);
        return ResponseEntity.ok()
                .body(SuccessResponse.of(MEMBER_PROFILE_GET_SUCCESS, memberProfileResponseDTO));
    }
}
