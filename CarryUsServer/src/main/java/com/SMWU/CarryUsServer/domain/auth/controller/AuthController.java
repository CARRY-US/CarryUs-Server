package com.SMWU.CarryUsServer.domain.auth.controller;

import com.SMWU.CarryUsServer.domain.auth.controller.dto.enums.AuthType;
import com.SMWU.CarryUsServer.domain.auth.controller.dto.request.MemberRequestDTO;
import com.SMWU.CarryUsServer.domain.auth.controller.dto.response.MemberAuthResponseDTO;
import com.SMWU.CarryUsServer.domain.auth.exception.AuthSuccessType;
import com.SMWU.CarryUsServer.domain.auth.service.AuthServiceProvider;
import com.SMWU.CarryUsServer.domain.auth.service.vo.MemberSignUpVO;
import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import com.SMWU.CarryUsServer.global.jwt.JwtService;
import com.SMWU.CarryUsServer.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceProvider authServiceProvider;
    private final JwtService jwtService;

    @PostMapping(value = "/auth/social/login", name = "소셜 로그인")
    public ResponseEntity<SuccessResponse<MemberAuthResponseDTO>> signUp(
            @RequestHeader(value = "Platform-token", required = false) final String platformToken,
            @RequestBody @Valid final MemberRequestDTO request) {
        MemberSignUpVO vo =
                authServiceProvider
                        .getAuthService(PlatformType.of(request.platformType()))
                        .saveMemberOrLogin(platformToken, request);
        MemberAuthResponseDTO responseDTO = jwtService.issueToken(vo);
        if (responseDTO.authType().equals(AuthType.SIGN_UP)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(SuccessResponse.of(AuthSuccessType.SIGN_UP_SUCCESS, responseDTO));
        }
        return ResponseEntity.ok().body(SuccessResponse.of(AuthSuccessType.LOGIN_SUCCESS, responseDTO));
    }
}
