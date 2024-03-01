package com.SMWU.CarryUsServer.global.security;

import com.SMWU.CarryUsServer.global.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.SMWU.CarryUsServer.global.security.config.SecurityConfig.UTF_8;

public class SecurityErrorUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void getErrorResponse(HttpServletResponse response, HttpStatus httpStatus, ErrorResponse errorResponse) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);
        String error = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(error);
    }
}
