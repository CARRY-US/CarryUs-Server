package com.SMWU.CarryUsServer.external.oauth.kakao;

import com.SMWU.CarryUsServer.domain.auth.controller.dto.enums.AuthType;
import com.SMWU.CarryUsServer.domain.auth.controller.dto.request.MemberRequestDTO;
import com.SMWU.CarryUsServer.domain.auth.service.AuthService;
import com.SMWU.CarryUsServer.domain.auth.service.vo.MemberSignUpVO;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import com.SMWU.CarryUsServer.domain.member.entity.enums.Role;
import com.SMWU.CarryUsServer.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        System.out.println("userInfoVO: " + userInfoVO.toString());

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
        } catch (Exception e) {
            log.error("getKakaoUserInfo error = {}", e);
            throw new InvalidPropertyException(KakaoAuthService.class, "getKakaoUserInfo", "카카오 사용자 정보를 가져오는데 실패했습니다.");
        }
    }
}
