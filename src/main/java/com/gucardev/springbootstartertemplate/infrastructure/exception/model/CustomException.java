package com.gucardev.springbootstartertemplate.infrastructure.exception.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final boolean logStackTrace;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.logStackTrace = true;
    }

    public CustomException(String message, HttpStatus status, boolean logStackTrace) {
        super(message);
        this.status = status;
        this.logStackTrace = logStackTrace;
    }
}