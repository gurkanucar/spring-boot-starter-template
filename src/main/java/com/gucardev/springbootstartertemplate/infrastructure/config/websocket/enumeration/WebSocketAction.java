package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum WebSocketAction {
    DO_SOMETHING_1(1),
    DO_SOMETHING_2(2),
    DO_SOMETHING_3(3),

    ERROR(99);

    private static final Map<Integer, WebSocketAction> ACTIONS_BY_CODE =
            Arrays.stream(values())
                    .collect(Collectors.toMap(WebSocketAction::getCode, Function.identity()));

    private final int code;

    public static WebSocketAction fromCode(int code) {
        return ACTIONS_BY_CODE.getOrDefault(code, ERROR);
    }
}