package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.WebSocketSessionService;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto.WebSocketMessage;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration.WebSocketAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;
import java.util.Optional;

@Slf4j
public abstract class AbstractWebSocketActionHandler<P, R> implements WebSocketActionHandler {

    protected final ObjectMapper objectMapper;
    protected final WebSocketSessionService sessionService;
    private final Class<P> payloadClass;
    private final ThreadLocal<WebSocketSession> currentSession = new ThreadLocal<>();

    protected AbstractWebSocketActionHandler(ObjectMapper objectMapper,
                                             WebSocketSessionService sessionService,
                                             Class<P> payloadClass) {
        this.objectMapper = objectMapper;
        this.sessionService = sessionService;
        this.payloadClass = payloadClass;
    }

    @Override
    public void handle(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try {
            currentSession.set(session);
            log.info("Processing {} message from user: {}",
                    message.getActionType(), getUsernameFromSession(session));
            handleInternal(session, message);
        } catch (Exception e) {
            log.error("Error processing WebSocket message: {}", e.getMessage());
            sendErrorResponse(session, getErrorPrefix() + ": " + e.getMessage());
        } finally {
            currentSession.remove();
        }
    }

    protected void handleInternal(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        P payload = tryParsePayload(message.getPayload(), payloadClass);
        if (payload == null) {
            sendErrorResponse(session, "Invalid " + payloadClass.getSimpleName() + " format");
            return;
        }

        log.info("Received {} payload: {}", payloadClass.getSimpleName(), payload);
        R response = processPayload(payload);
        sendResponse(session, getResponseAction(), response);
    }

    protected abstract R processPayload(P payload) throws Exception;

    protected abstract String getErrorPrefix();

    protected abstract WebSocketAction getResponseAction();

    protected <T> T tryParsePayload(Object payload, Class<T> targetClass) {
        try {
            return objectMapper.convertValue(payload, targetClass);
        } catch (Exception e) {
            log.warn("Failed to parse {} payload: {}", targetClass.getSimpleName(), e.getMessage());
            return null;
        }
    }

    /**
     * Sends a response to the given session using the provided action and payload
     */
    protected void sendResponse(WebSocketSession session, WebSocketAction action, R response) throws Exception {
        sessionService.sendMessageToSession(session, action, response);
    }

    /**
     * Sends an error response to the given session
     */
    protected void sendErrorResponse(WebSocketSession session, String message) throws Exception {
        sessionService.sendErrorToSession(session, message);
    }

    /**
     * Sends a message to a specific user
     */
    protected void sendToUser(String username, WebSocketAction action, Object payload) throws Exception {
        sessionService.sendMessageToUser(username, action, payload);
    }

    /**
     * Broadcasts a message to all connected users
     */
    protected <T> void broadcastAll(WebSocketAction action, T payload) throws Exception {
        sessionService.broadcastAll(action, payload);
    }

    /**
     * Broadcasts a message to all users except the current session user
     */
    protected <T> void broadcastOthers(WebSocketAction action, T payload) throws Exception {
        WebSocketSession session = currentSession.get();
        if (session != null) {
            sessionService.broadcastOthers(session, action, payload);
        } else {
            log.warn("No active session found when attempting to broadcast to others");
            sessionService.broadcastAll(action, payload);
        }
    }

    /**
     * Broadcasts a message to all users except the specified user
     */
    protected <T> void broadcastOthers(String excludeUsername, WebSocketAction action, T payload) throws Exception {
        sessionService.broadcastOthers(excludeUsername, action, payload);
    }

    /**
     * Gets the username from the current session
     */
    protected String getUsernameFromSession(WebSocketSession session) {
        Principal principal = session.getPrincipal();
        return principal != null ? principal.getName() : "unknown";
    }

    /**
     * Gets the current active session
     */
    protected Optional<WebSocketSession> getActiveSession() {
        return Optional.ofNullable(currentSession.get());
    }

    /**
     * Gets the username from the current active session
     */
    protected String getActiveUsername() {
        WebSocketSession session = currentSession.get();
        return session != null ? getUsernameFromSession(session) : "unknown";
    }
}