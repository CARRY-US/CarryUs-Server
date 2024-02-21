package com.SMWU.CarryUsServer.domain.auth.jwt;

import com.SMWU.CarryUsServer.domain.auth.controller.dto.MemberAuthResponseDTO;
import com.SMWU.CarryUsServer.domain.auth.controller.dto.MemberReissueResponseDTO;
import com.SMWU.CarryUsServer.domain.auth.exception.AuthException;
import com.SMWU.CarryUsServer.domain.auth.service.vo.MemberSignUpVO;
import com.SMWU.CarryUsServer.domain.member.entity.enums.Role;
import com.SMWU.CarryUsServer.domain.member.repository.MemberRepoistory;
import com.SMWU.CarryUsServer.external.redis.RedisTokenRepository;
import com.SMWU.CarryUsServer.external.redis.RedisTokenVO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.*;
import static com.SMWU.CarryUsServer.domain.auth.jwt.TokenConstants.BEARER;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private final JwtTokenManager jwtTokenManager;
    private final MemberRepoistory memberRepoistory;
    private final RedisTokenRepository redisTokenRepository;

    @Transactional
    public MemberAuthResponseDTO issueToken(MemberSignUpVO vo) {
        String accessToken = jwtTokenManager.createAccessToken(vo.memberId());

        if (vo.role().equals(Role.TRAVELER)|vo.role().equals(Role.STORE_OWNER)) {
            String refreshToken = jwtTokenManager.createRefreshToken();
            updateRefreshToken(vo.memberId(), refreshToken);
            return MemberAuthResponseDTO.of(vo.memberId(), vo.authType(), vo.role(), accessToken, refreshToken);
        }

        throw new AuthException(UNAUTHORIZED_MEMBER_LOGIN);
    }

    @Transactional
    public MemberReissueResponseDTO reissueToken(HttpServletRequest request) {
        String refreshToken = extractRefreshToken(request).orElseThrow(()->new RuntimeException());
        String accessToken = extractAccessToken(request).orElseThrow();

        try {
            jwtTokenManager.validateToken(refreshToken);
        } catch (MalformedJwtException | SignatureException e) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        } catch (ExpiredJwtException e){
            throw new AuthException(UNAUTHORIZED_REFRESH_TOKEN);
        }

        String redisTokenMemberId = jwtTokenManager.extractMemberIdFromAccessToken(accessToken);
        RedisTokenVO foundRefreshToken = redisTokenRepository.findByMemberId(redisTokenMemberId).orElseThrow(()->new RuntimeException());

        if (!foundRefreshToken.getRefreshToken().equals(refreshToken)) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }

        Long memberId = Long.valueOf(redisTokenMemberId);

        String newAccessToken = jwtTokenManager.createAccessToken(memberId);
        String newRefreshToken = jwtTokenManager.createRefreshToken();

        updateRefreshToken(memberId, newRefreshToken);

        return MemberReissueResponseDTO.of(memberId, newAccessToken, newRefreshToken);
    }

    public void updateRefreshToken(Long memberId, String newRefreshToken) {
        RedisTokenVO refreshTokenVO = redisTokenRepository.findByMemberId(String.valueOf(memberId)).orElse(null);

        if(refreshTokenVO != null){
            refreshTokenVO.updateRefreshToken(newRefreshToken);
            redisTokenRepository.save(refreshTokenVO);
            return;
        }

        redisTokenRepository.save(RedisTokenVO.builder()
                .memberId(String.valueOf(memberId))
                .refreshToken(newRefreshToken)
                .build());
    }

    private Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    private Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }
}