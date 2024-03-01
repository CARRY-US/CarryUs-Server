package com.SMWU.CarryUsServer.global.security.handler;

import com.SMWU.CarryUsServer.global.response.ErrorResponse;
import com.SMWU.CarryUsServer.global.security.SecurityErrorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.*;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException{
        SecurityErrorUtils.getErrorResponse(response, HttpStatus.UNAUTHORIZED, ErrorResponse.of(UNAUTHORIZED_MEMBER));
    }
}
