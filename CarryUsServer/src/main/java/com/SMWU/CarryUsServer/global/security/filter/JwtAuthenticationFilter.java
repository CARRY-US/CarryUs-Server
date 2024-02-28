package com.SMWU.CarryUsServer.global.security.filter;

import com.SMWU.CarryUsServer.domain.auth.exception.AuthException;
import com.SMWU.CarryUsServer.global.jwt.JwtTokenManager;
import com.SMWU.CarryUsServer.global.jwt.util.RequestUtils;
import com.SMWU.CarryUsServer.global.security.service.MemberAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.INVALID_ACCESS_TOKEN;
import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.UNAUTHORIZED_ACCESS_TOKEN;
import static com.SMWU.CarryUsServer.global.security.config.SecurityConfig.AUTH_WHITELIST;
import static com.SMWU.CarryUsServer.global.security.config.SecurityConfig.AUTH_WHITELIST_WILDCARD;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final MemberAuthService memberAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (Arrays.stream(AUTH_WHITELIST)
                .anyMatch(whiteUrl -> request.getRequestURI().equals(whiteUrl))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (Arrays.stream(AUTH_WHITELIST_WILDCARD)
                .anyMatch(
                        whiteUrl -> request.getRequestURI().startsWith(whiteUrl.substring(0, whiteUrl.length() - 3)))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!RequestUtils.isContainsAccessToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationAccessToken = RequestUtils.getAuthorizationAccessToken(request);

        try {
            jwtTokenManager.validateToken(authorizationAccessToken);

            String memberId = jwtTokenManager.extractMemberIdFromAccessToken(authorizationAccessToken);
            UserDetails userDetails = memberAuthService.loadUserByUsername(memberId);

            Authentication authentication
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (MalformedJwtException | SignatureException e) {
            throw new AuthException(INVALID_ACCESS_TOKEN);
        } catch (ExpiredJwtException e){
            throw new AuthException(UNAUTHORIZED_ACCESS_TOKEN);
        }
        catch (AuthException e) {
            throw new AuthException(e.getExceptionType());
        }

        filterChain.doFilter(request, response);
    }
}
