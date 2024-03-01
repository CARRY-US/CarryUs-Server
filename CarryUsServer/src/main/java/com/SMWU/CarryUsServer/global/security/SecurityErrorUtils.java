package com.SMWU.CarryUsServer.global.security;

import com.SMWU.CarryUsServer.global.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.FORBIDDEN_MEMBER;
import static com.SMWU.CarryUsServer.global.security.config.SecurityConfig.UTF_8;

public class SecurityErrorUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static HttpServletResponse getErrorResponse(HttpServletResponse response, HttpStatus httpStatus, ErrorResponse errorResponse) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);
        String error = objectMapper.writeValueAsString(ErrorResponse.of(FORBIDDEN_MEMBER));
        response.getWriter().write(error);
        return response;
    }
}
