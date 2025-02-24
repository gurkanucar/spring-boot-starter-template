package com.gucardev.springbootstartertemplate.infrastructure.exception.helper;

import com.gucardev.springbootstartertemplate.infrastructure.exception.model.ExceptionResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Slf4j
@NoArgsConstructor
public abstract class BaseExceptionHandler {

    private static final String TRACE_ID_LOG_VAR_NAME = "traceId";

    protected final ResponseEntity<ExceptionResponse> buildErrorResponse(Object error, HttpStatus status, WebRequest request, Map<String, String> validationErrors) {
        String path = extractPath(request);
        String traceId = MDC.get(TRACE_ID_LOG_VAR_NAME);
        var errorResponse = ExceptionResponse.buildResponse(
                status,
                error,
                path,
                traceId,
                validationErrors
        );
        log.warn(errorResponse.toString());
        return errorResponse;
    }

    protected final ResponseEntity<ExceptionResponse> buildErrorResponse(Object error, HttpStatus status, WebRequest request) {
        return buildErrorResponse(error, status, request, null);
    }

    private String extractPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
