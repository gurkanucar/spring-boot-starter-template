package com.gucardev.springbootstartertemplate.infrastructure.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientRequestException extends RuntimeException {

    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public ClientRequestException(String message) {
        super(message);
    }
}
