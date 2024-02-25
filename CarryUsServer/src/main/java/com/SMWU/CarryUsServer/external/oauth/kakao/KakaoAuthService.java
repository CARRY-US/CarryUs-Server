package com.SMWU.CarryUsServer.external.oauth.kakao;

import com.SMWU.CarryUsServer.domain.auth.controller.dto.enums.AuthType;
import com.SMWU.CarryUsServer.domain.auth.controller.dto.request.MemberRequestDTO;
import com.SMWU.CarryUsServer.domain.auth.exception.AuthException;
import com.SMWU.CarryUsServer.domain.auth.service.AuthService;
import com.SMWU.CarryUsServer.domain.auth.service.vo.MemberSignUpVO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import com.SMWU.CarryUsServer.domain.member.entity.enums.Role;
import com.SMWU.CarryUsServer.domain.member.repository.MemberRepository;
import com.SMWU.CarryUsServer.global.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.AccessDeniedException;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE;

@Slf4j
@Service
public class KakaoAuthService extends AuthService {
    @Value("${spring.security.oauth2.client.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    public KakaoAuthService(MemberRepository memberRepository) {
        super(memberRepository);
    }

    @Override
    public MemberSignUpVO saveMemberOrLogin(String platformToken, MemberRequestDTO request) {
        KakaoMemberVO userInfoVO = getKakaoUserInfo(platformToken);
        Member foundMember =
                getMember(PlatformType.of(request.platformType()), userInfoVO.id().toString());

        if (foundMember != null) {
            return MemberSignUpVO.of(foundMember, PlatformType.KAKAO, Role.of(request.role()), AuthType.LOGIN);
        }

        Member savedMember = saveUser(request, userInfoVO.id().toString(), userInfoVO.kakaoAccount().profile().nickname(), null, userInfoVO.kakaoAccount().profile().thumbnailImageUrl());
        return MemberSignUpVO.of(savedMember, PlatformType.KAKAO, Role.of(request.role()), AuthType.SIGN_UP);
    }

    private KakaoMemberVO getKakaoUserInfo(final String accessToken) {
        WebClient webClient = WebClient.builder().build();
        try {
            return webClient.post()
                    .uri(kakaoUserInfoUri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(KakaoMemberVO.class)
                    .block();
        } catch (HttpClientErrorException.BadRequest e) {
            throw new AuthException(INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE);
        }
    }
}
