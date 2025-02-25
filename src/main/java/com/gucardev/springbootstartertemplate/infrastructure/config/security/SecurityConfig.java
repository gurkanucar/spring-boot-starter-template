package com.gucardev.springbootstartertemplate.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] IGNORED_PATHS = {
            "/**", // disable later
            "/success",
            "/swagger-resources/**",
            "/swagger-ui.html/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/auth/login",
            "/auth/register",
            "/auth/refresh",
            "/public/**",
            "/",
            "/timezone",
            "/time",
            "/hello",
            "/log-level",
            "/h2-console/**",
            // Actuator endpoints
            "/git-information",
            "/actuator/**",
            "/actuator/prometheus",
            "/actuator/health",
            "/actuator/info",
            "/actuator/metrics"
    };

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> Arrays.stream(IGNORED_PATHS)
                .forEach(path -> web.ignoring().requestMatchers(new AntPathRequestMatcher(path)));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(x -> x
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(x -> x.anyRequest().authenticated());
        return httpSecurity.build();
    }

}
