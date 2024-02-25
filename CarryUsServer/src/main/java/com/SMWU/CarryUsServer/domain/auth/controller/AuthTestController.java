package com.SMWU.CarryUsServer.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/test")
public class AuthTestController {

    @Value("${spring.security.oauth2.client.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.kakao.redirect-uri}")
    private String redirectUri = "http://localhost:8080/test/kakao/callback";

    @GetMapping("/authTest")
    public String authTest(HttpServletRequest request, HttpServletResponse response) {
        String kakaoRedirectURL = "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoClientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";

        try {
            response.sendRedirect(kakaoRedirectURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "SUCCESS";
    }

    @GetMapping("/kakao/access-token")
    public ResponseEntity<String> getKakaoAccessToken(@RequestParam("code") String code) {
            WebClient webClient = WebClient.create("https://kauth.kakao.com");

        String responseBody = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", kakaoClientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(responseBody);
    }

}
