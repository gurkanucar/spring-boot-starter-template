package com.gucardev.springbootstartertemplate.infrastructure.exception;

import com.gucardev.springbootstartertemplate.infrastructure.config.message.MessageUtil;
import com.gucardev.springbootstartertemplate.infrastructure.exception.helper.BaseExceptionHandler;
import com.gucardev.springbootstartertemplate.infrastructure.exception.model.ClientRequestException;
import com.gucardev.springbootstartertemplate.infrastructure.exception.model.CustomException;
import com.gucardev.springbootstartertemplate.infrastructure.exception.model.ExceptionResponse;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class GlobalBaseExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException exception, WebRequest request) {
        // Check if we should log the stack trace for this exception
        if (exception.isLogStackTrace()) {
            log.error("Custom exception occurred: ", exception);
        }
        return this.buildErrorResponse(exception.getMessage(), exception.getStatus(), request);
    }

    @ExceptionHandler(ClientRequestException.class)
    public ResponseEntity<ExceptionResponse> clientRequestException(ClientRequestException exception, WebRequest request) {
        if (exception.isLogStackTrace()) {
            log.error("Client request exception occurred: ", exception);
        } else {
            log.error("Client request exception occurred: {}", exception.getMessage());
        }
        return this.buildErrorResponse(exception.getMessage(), exception.getStatus(), request);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ExceptionResponse> noResourceFoundException(NoResourceFoundException exception, WebRequest request) {
        log.warn("Resource not found: {}", exception.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        return this.buildErrorResponse(exception.getMessage(), status, request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidEx(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = extractFieldErrors(ex);
        log.warn("Validation failed: {}", errors);
        return this.buildErrorResponse(MessageUtil.getMessage("validation.failed"), HttpStatus.BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        return this.buildErrorResponse(exception.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<ExceptionResponse> handleConstraintViolationEx(
            ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = extractConstraintViolations(ex);
        log.warn("Constraint violation: {}", errors);
        return this.buildErrorResponse(MessageUtil.getMessage("validation.failed"), HttpStatus.BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = MessageUtil.getMessage("error.request.body.missing");
        log.warn("Message not readable: {}", ex.getMessage());
        return this.buildErrorResponse(errorMessage, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
        String message;
        if (isDatabaseException(ex)) {
            log.error("Database exception occurred: ", ex);
            message = "database.error";
        } else {
            log.error("Exception occurred: ", ex);
            message = "default.error";
        }
        return this.buildErrorResponse(MessageUtil.getMessage(message), HttpStatus.BAD_REQUEST, request);
    }

    private Map<String, String> extractFieldErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private Map<String, String> extractConstraintViolations(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private boolean isDatabaseException(Throwable ex) {
        return ex instanceof SQLException ||
                ex instanceof DataAccessException ||
                ex instanceof PersistenceException ||
                (ex.getCause() != null && isDatabaseException(ex.getCause()));
    }
}

