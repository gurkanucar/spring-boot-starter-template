package com.gucardev.springbootstartertemplate.infrastructure.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory.WebSocketActionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketSessionService sessionService;
    private final WebSocketActionFactory actionFactory;
    private final ObjectMapper objectMapper;

    @Bean
    public CustomSecureWebSocketHandler webSocketHandler() {
        return new CustomSecureWebSocketHandler(sessionService, actionFactory, objectMapper);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws")
                .setAllowedOriginPatterns("*");
    }
}