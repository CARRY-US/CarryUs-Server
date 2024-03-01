package com.SMWU.CarryUsServer.global.security.filter;

import com.SMWU.CarryUsServer.domain.auth.exception.AuthException;
import com.SMWU.CarryUsServer.global.response.ErrorResponse;
import com.SMWU.CarryUsServer.global.security.SecurityErrorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;

import static com.SMWU.CarryUsServer.global.security.config.SecurityConfig.UTF_8;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            SecurityErrorUtils.getErrorResponse(response, HttpStatus.UNAUTHORIZED, ErrorResponse.of(e.getExceptionType()));
        }
    }
}
