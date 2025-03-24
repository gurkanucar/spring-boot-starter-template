package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.commandhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.WebSocketSessionService;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration.WebSocketAction;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.factory.AbstractWebSocketActionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DoSomething2ActionHandler extends AbstractWebSocketActionHandler<
        DoSomething2ActionHandler.DoSomething2ActionPayload,
        DoSomething2ActionHandler.DoSomething2ActionResponse> {

    public DoSomething2ActionHandler(ObjectMapper objectMapper, WebSocketSessionService sessionService) {
        super(objectMapper, sessionService, DoSomething2ActionPayload.class);
    }

    @Override
    protected DoSomething2ActionResponse processPayload(DoSomething2ActionPayload payload) {
        log.info("Processing action for param1: {}", payload.param1());
        // Your business logic here
        return new DoSomething2ActionResponse(
                payload.param1(),
                payload.status(),
                payload.mapOfParams(),
                payload.listOfParams()
        );
    }

    @Override
    protected String getErrorPrefix() {
        return "DoSomething2Action Status Error";
    }

    @Override
    protected WebSocketAction getResponseAction() {
        return WebSocketAction.DO_SOMETHING_2;
    }

    public record DoSomething2ActionPayload(
            String param1,
            String status,
            Map<String, Integer> mapOfParams,
            List<String> listOfParams) {
    }

    public record DoSomething2ActionResponse(
            String param1,
            String status,
            Map<String, Integer> mapOfParams,
            List<String> listOfParams) {
    }
}
