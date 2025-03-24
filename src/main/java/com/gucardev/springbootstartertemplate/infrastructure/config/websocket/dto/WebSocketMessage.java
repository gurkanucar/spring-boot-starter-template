package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration.WebSocketAction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebSocketMessage<T> {
    private int action;
    private T payload;

    @JsonIgnore
    private WebSocketAction actionType;

    public WebSocketMessage(WebSocketAction actionType, T payload) {
        this.actionType = actionType;
        this.action = actionType.getCode();
        this.payload = payload;
    }

    public void setAction(int action) {
        this.action = action;
        this.actionType = WebSocketAction.fromCode(action);
    }

    public WebSocketAction getActionType() {
        if (actionType == null) {
            actionType = WebSocketAction.fromCode(action);
        }
        return actionType;
    }
}