package com.gucardev.springbootstartertemplate.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {
    DEFAULT_EXCEPTION("messages.error.default_message", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_EXCEPTION("messages.error.already_exists_exception", HttpStatus.BAD_REQUEST),
    NOT_FOUND_EXCEPTION("messages.error.not_found_exception", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION("messages.error.user_not_found_exception", HttpStatus.NOT_FOUND),
    ACCESS_DENIED_EXCEPTION("messages.error.access_denied_exception", HttpStatus.UNAUTHORIZED),
    FORBIDDEN_EXCEPTION("messages.error.forbidden_exception", HttpStatus.FORBIDDEN),
    AUTHENTICATION_FAILED("messages.error.authentication_failed", HttpStatus.UNAUTHORIZED),
    TOO_MANY_REQUESTS_EXCEPTION("messages.error.too_many_requests", HttpStatus.TOO_MANY_REQUESTS),
    INSUFFICIENT_FUNDS_EXCEPTION("messages.error.insufficient_fund", HttpStatus.UNAUTHORIZED);

    private final String key;
    private final HttpStatus status;

    ExceptionMessage(String key, HttpStatus httpStatus) {
        this.key = key;
        this.status = httpStatus;
    }
}