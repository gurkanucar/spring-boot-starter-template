package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.commandhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.WebSocketSessionService;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration.WebSocketAction;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory.AbstractWebSocketActionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DoSomething1ActionHandler extends AbstractWebSocketActionHandler<
        DoSomething1ActionHandler.DoSomething1ActionPayload,
        Map<String, Object>> {

    public DoSomething1ActionHandler(ObjectMapper objectMapper, WebSocketSessionService sessionService) {
        super(objectMapper, sessionService, DoSomething1ActionPayload.class);
    }

    @Override
    protected Map<String, Object> processPayload(DoSomething1ActionPayload payload) throws Exception {
        doSomething(payload);

        // Create response data
        Map<String, Object> response = new HashMap<>();
        response.put("acknowledged", true);

        // Add session info if available
        getActiveSession().ifPresent(session -> {
            response.put("sessionId", session.getId());
            response.put("username", getUsernameFromSession(session));
        });

        // Send a direct message to a specific user
        sendToUser(payload.param1(), WebSocketAction.DO_SOMETHING_2,
                Map.of("status", "OK", "message", "Action processed for you"));

        // Broadcast to all users except the current user
        broadcastOthers(WebSocketAction.DO_SOMETHING_3,
                Map.of("notification", "User " + getActiveUsername() + " performed an action",
                        "timestamp", System.currentTimeMillis()));

        // Could also broadcast to all including current user
        // broadcastAll(WebSocketAction.DO_SOMETHING_GLOBAL, Map.of("globalUpdate", true));

        // Return response for the current user
        return response;
    }

    private void doSomething(DoSomething1ActionPayload payload) {
        log.info("doSomething {}", payload);
    }

    @Override
    protected String getErrorPrefix() {
        return "DoSomething1Action Status Error";
    }

    @Override
    protected WebSocketAction getResponseAction() {
        return WebSocketAction.DO_SOMETHING_1;
    }

    public record DoSomething1ActionPayload(
            String param1,
            String status,
            Map<String, Integer> mapOfParams,
            List<String> listOfParams) {
    }
}