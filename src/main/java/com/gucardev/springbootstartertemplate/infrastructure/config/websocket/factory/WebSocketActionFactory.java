package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory;


import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.commandhandler.DoSomething1ActionHandler;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.commandhandler.DoSomething2ActionHandler;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration.WebSocketAction;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketActionFactory {
    private final DoSomething1ActionHandler doSomething1ActionHandler;
    private final DoSomething2ActionHandler doSomething2ActionHandler;
    // Add other handlers here

    private final Map<WebSocketAction, WebSocketActionHandler> handlers = new EnumMap<>(WebSocketAction.class);

    @PostConstruct
    private void initHandlers() {
        handlers.put(WebSocketAction.DO_SOMETHING_1, doSomething1ActionHandler);
        handlers.put(WebSocketAction.DO_SOMETHING_2, doSomething2ActionHandler);
        // Register other handlers here

        log.info("Initialized {} WebSocket action handlers", handlers.size());
    }

    public WebSocketActionHandler getHandler(WebSocketAction action) {
        WebSocketActionHandler handler = handlers.get(action);
        if (handler == null) {
            log.warn("No handler found for action: {}", action);
        }

        return handler;
    }
}
