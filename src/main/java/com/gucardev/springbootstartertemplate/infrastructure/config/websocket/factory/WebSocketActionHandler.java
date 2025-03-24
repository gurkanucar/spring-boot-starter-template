package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory;

import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketActionHandler {
    void handle(WebSocketSession session, WebSocketMessage<?> message) throws Exception;
}