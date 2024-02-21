package com.SMWU.CarryUsServer.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.SMWU.CarryUsServer.global.jwt.TokenConstants.*;

@Component
@Slf4j
public class JwtTokenManager {
    private final Key secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    public JwtTokenManager(@Value("${jwt.secretKey}") String secretKey, ObjectMapper objectMapper) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(final Long memberId) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .claim(ID_CLAIM, memberId)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenExpirationPeriod))  //토큰 만료 시간 설정
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationPeriod))  //토큰 만료 시간 설정
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }



    public Boolean validateToken(final String atk) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        Claims tokenClaims = getTokenClaims(atk);
        return !tokenClaims.getExpiration().before(new Date());
    }

    public String extractMemberIdFromAccessToken(final String ack){
        Claims tokenClaims = getTokenClaims(ack);
        return tokenClaims.get(ID_CLAIM).toString();
    }

    private Claims getTokenClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}