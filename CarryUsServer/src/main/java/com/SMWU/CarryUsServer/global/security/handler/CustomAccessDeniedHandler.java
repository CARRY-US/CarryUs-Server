package com.SMWU.CarryUsServer.global.security.handler;

import com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType;
import com.SMWU.CarryUsServer.global.response.ErrorResponse;
import com.SMWU.CarryUsServer.global.security.SecurityErrorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.FORBIDDEN_MEMBER;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        SecurityErrorUtils.getErrorResponse(response, HttpStatus.FORBIDDEN, ErrorResponse.of(FORBIDDEN_MEMBER));
    }
}
