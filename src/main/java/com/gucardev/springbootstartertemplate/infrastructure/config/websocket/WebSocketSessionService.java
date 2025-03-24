package com.gucardev.springbootstartertemplate.infrastructure.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto.ErrorResponse;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto.WebSocketMessage;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration.WebSocketAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketSessionService {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public void addSession(String userId, WebSocketSession session) {
        log.debug("Adding WebSocket session for user: {}", userId);
        sessions.put(userId, session);
    }

    public void removeSession(String userId) {
        WebSocketSession removedSession = sessions.remove(userId);
        if (removedSession != null) {
            log.debug("Removed WebSocket session for user: {}", userId);
        } else {
            log.warn("Attempted to remove non-existent WebSocket session for user: {}", userId);
        }
    }

    public Optional<WebSocketSession> getSession(String userId) {
        WebSocketSession session = sessions.get(userId);
        if (session == null) {
            log.debug("No WebSocket session found for user: {}", userId);
        }
        return Optional.ofNullable(session);
    }

    public boolean hasSession(String userId) {
        return sessions.containsKey(userId);
    }

    public int getSessionCount() {
        return sessions.size();
    }

    public Set<String> getActiveUsers() {
        return new HashSet<>(sessions.keySet());
    }


    private <T> TextMessage createTextMessage(WebSocketAction action, T payload) throws IOException {
        WebSocketMessage<T> message = new WebSocketMessage<>(action, payload);
        String jsonMessage = objectMapper.writeValueAsString(message);
        return new TextMessage(jsonMessage);
    }

    public <T> void broadcastAll(WebSocketAction action, T payload) throws IOException {
        TextMessage textMessage = createTextMessage(action, payload);

        int sentCount = 0;
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(textMessage);
                sentCount++;
            }
        }
        log.info("Broadcast {} message to {} active sessions", action, sentCount);
    }

    public <T> void broadcastOthers(String senderUsername, WebSocketAction action, T payload) throws IOException {
        TextMessage textMessage = createTextMessage(action, payload);

        int sentCount = 0;
        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            String username = entry.getKey();
            WebSocketSession session = entry.getValue();

            if (!username.equals(senderUsername) && session.isOpen()) {
                session.sendMessage(textMessage);
                sentCount++;
            }
        }
        log.info("Broadcast {} message to {} other sessions (excluding {})", action, sentCount, senderUsername);
    }

    public <T> void broadcastOthers(WebSocketSession senderSession, WebSocketAction action, T payload) throws IOException {
        String senderUsername = getSenderUsername(senderSession);
        broadcastOthers(senderUsername, action, payload);
    }


    public <T> void sendMessageToUser(String username, WebSocketAction action, T payload) throws IOException {
        var session = getSession(username);
        if (session.isPresent() && session.get().isOpen()) {
            TextMessage textMessage = createTextMessage(action, payload);
            session.get().sendMessage(textMessage);
            log.info("Sent {} message to user {}", action, username);
        } else {
            log.warn("Cannot send message to user {}. Session not found or closed.", username);
        }
    }


    public <T> void sendMessageToSession(WebSocketSession session, WebSocketAction action, T payload) throws IOException {
        if (session != null && session.isOpen()) {
            TextMessage textMessage = createTextMessage(action, payload);
            session.sendMessage(textMessage);
            log.info("Sent {} message to session {}", action, session.getId());
        } else {
            log.warn("Cannot send message to session. Session is null or closed.");
        }
    }


    public void sendErrorToUser(String username, String errorMessage) throws IOException {
        ErrorResponse error = new ErrorResponse(errorMessage);
        sendMessageToUser(username, WebSocketAction.ERROR, error);
    }

    public void sendErrorToSession(WebSocketSession session, String errorMessage) throws IOException {
        ErrorResponse error = new ErrorResponse(errorMessage);
        sendMessageToSession(session, WebSocketAction.ERROR, error);
    }


    public void sendMessageToUser(String username, String message) throws IOException {
        var session = getSession(username);
        if (session.isPresent() && session.get().isOpen()) {
            session.get().sendMessage(new TextMessage(message));
        } else {
            log.warn("Cannot send message to user {}. Session not found or closed.", username);
        }
    }


    public void broadcastMessage(String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(textMessage);
            }
        }
    }


    private String getSenderUsername(WebSocketSession session) {
        if (session == null || session.getPrincipal() == null) {
            return "unknown";
        }
        return session.getPrincipal().getName();
    }

    public void clearAllSessions() {
        log.info("Clearing all WebSocket sessions. Count before clearing: {}", sessions.size());
        sessions.clear();
    }
}