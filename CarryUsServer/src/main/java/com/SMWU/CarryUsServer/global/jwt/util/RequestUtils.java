package com.SMWU.CarryUsServer.global.jwt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestUtils {
    @Value("${jwt.access.header}")
    private static String accessHeader = "Authorization";
    public static final String BEARER_HEADER = "Bearer ";


    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isContainsAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(accessHeader);
        return authorization != null
                && authorization.startsWith(BEARER_HEADER);
    }

    public static String getAuthorizationAccessToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION).substring(7);
    }
}
