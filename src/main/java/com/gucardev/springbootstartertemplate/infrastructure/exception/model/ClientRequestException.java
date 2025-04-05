package com.gucardev.springbootstartertemplate.infrastructure.exception.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ClientRequestException extends RuntimeException {
    private final HttpStatus status;
    private final boolean logStackTrace;

    public ClientRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.logStackTrace = true;
    }
}