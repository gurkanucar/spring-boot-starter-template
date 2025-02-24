package com.gucardev.springbootstartertemplate.infrastructure.config.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class CustomSecureWebSocketHandler extends AbstractWebSocketHandler {
    private final WebSocketSessionService sessionService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        var principal = session.getPrincipal();

        if (principal == null || principal.getName() == null) {
            // Send a message to the client before closing the connection
            TextMessage message = new TextMessage("User must be authenticated");
            session.sendMessage(message);

            session.close(CloseStatus.SERVER_ERROR.withReason("User must be authenticated"));
            return;
        }

        sessionService.addSession(principal.getName(), session);
        log.info("Connection established with user: {}", principal.getName());
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        var principal = session.getPrincipal();
        sessionService.removeSession(principal.getName());
        log.info("Connection closed with user: {}", principal.getName());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        var principal = session.getPrincipal();
        if (principal != null) {
            log.info("Received message from user {}: {}", principal.getName(), message.getPayload());

            // Deserialize the message
            WebSocketMessage<?> webSocketMessage =
                    new ObjectMapper().readValue(message.getPayload(), WebSocketMessage.class);

        }
    }
}
