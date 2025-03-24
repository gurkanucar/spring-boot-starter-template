package com.gucardev.springbootstartertemplate.infrastructure.config.websocket.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String code = "GENERAL_ERROR";

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }
}