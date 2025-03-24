package com.gucardev.springbootstartertemplate.infrastructure.config.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto.ErrorResponse;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto.WebSocketMessage;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory.WebSocketActionFactory;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory.WebSocketActionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@Slf4j
public class CustomSecureWebSocketHandler extends AbstractWebSocketHandler {
    private final WebSocketSessionService sessionService;
    private final WebSocketActionFactory actionFactory;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        var principal = session.getPrincipal();

        if (principal == null || principal.getName() == null) {
            sendErrorAndClose(session, "User must be authenticated");
            log.warn("User must be authenticated");
            return;
        }

        sessionService.addSession(principal.getName(), session);
        log.info("Connection established with user: {}", principal.getName());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Principal principal = session.getPrincipal();
        if (principal != null) {
            sessionService.removeSession(principal.getName());
            log.info("Connection closed with user: {}", principal.getName());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Principal principal = session.getPrincipal();
        if (principal == null) {
            sendErrorAndClose(session, "User must be authenticated");
            return;
        }

        log.info("Received message from user {}: {}", principal.getName(), message.getPayload());

        try {
            // Parse the message
            WebSocketMessage<?> webSocketMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);

            // Find and execute the appropriate handler
            WebSocketActionHandler handler = actionFactory.getHandler(webSocketMessage.getActionType());
            if (handler != null) {
                handler.handle(session, webSocketMessage);
            } else {
                sendError(session, "No handler found for action: " + webSocketMessage.getActionType());
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            sendError(session, "Failed to process message: " + e.getMessage());
        }
    }

    private void sendError(WebSocketSession session, String errorMessage) throws IOException {
        ErrorResponse error = new ErrorResponse(errorMessage);
        WebSocketMessage<ErrorResponse> message = new WebSocketMessage<>(
                com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration.WebSocketAction.ERROR, error);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }

    private void sendErrorAndClose(WebSocketSession session, String errorMessage) throws IOException {
        sendError(session, errorMessage);
        session.close(CloseStatus.NOT_ACCEPTABLE.withReason(errorMessage));
    }
}
