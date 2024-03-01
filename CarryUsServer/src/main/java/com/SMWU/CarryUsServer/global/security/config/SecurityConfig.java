package com.SMWU.CarryUsServer.global.security.config;

import com.SMWU.CarryUsServer.global.security.filter.JwtAuthenticationFilter;
import com.SMWU.CarryUsServer.global.security.filter.JwtExceptionFilter;
import com.SMWU.CarryUsServer.global.security.handler.CustomAccessDeniedHandler;
import com.SMWU.CarryUsServer.global.security.handler.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String[] AUTH_WHITELIST = {
            "/", "/error",
            "/favicon.ico",
            "/actuator/health", "/check/profile"
    };

    public static final String[] AUTH_WHITELIST_WILDCARD = {
            "/webjars/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/auth/**",
            "/css/**", "/images/**", "/js/**",
            "/h2-console/**",
            "/test/**"
    };

    public static final String UTF_8 = "UTF-8";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headerConfig ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                );


        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.requestMatchers(AUTH_WHITELIST_WILDCARD).permitAll();
                    auth.requestMatchers("/main/**", "/search/**", "/stores/**", "/reservation/**", "/my/**", "/reviews/**").hasAuthority("TRAVELER");
                    auth.anyRequest().authenticated();
                })
              .exceptionHandling(exceptionConfig ->
                        exceptionConfig.authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler())
              );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}