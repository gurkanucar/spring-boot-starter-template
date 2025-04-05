package com.gucardev.springbootstartertemplate.infrastructure.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.stream.Collectors;


@Slf4j
@Service
public class HmacAuthService {
    private static final long TIMESTAMP_TOLERANCE_MS = 3_000; // X seconds tolerance

    /**
     * Validates the signature from the incoming request
     * Returns true if the signature is valid or if no Authorization header is present
     */
    public boolean isValidSignature(HttpServletRequest request) {
        // Extract the Authorization header to use as secret
        String authHeader = request.getHeader("Authorization");

        // Skip HMAC validation if no Authorization header is provided
        if (authHeader == null || authHeader.isEmpty()) {
            log.debug("No Authorization header, skipping HMAC validation");
            return true;
        }

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String timestamp = request.getHeader("X-App-Timestamp");
        String nonce = request.getHeader("X-App-Nonce");
        String signature = request.getHeader("X-App-Signature");

        // Validate required headers
        if (timestamp == null || signature == null || nonce == null) {
            log.warn("Missing required headers for HMAC authentication");
            return false;
        }

        // Validate timestamp
        if (!isTimestampValid(timestamp)) {
            log.warn("Invalid timestamp: {}", timestamp);
            return false;
        }

        // Get request body for non-GET requests
        String body = "";
        if (!method.equals("GET")) {
            try {
                // Read the body for POST/PUT requests
                body = request.getReader().lines().collect(Collectors.joining());
            } catch (IOException e) {
                log.error("Failed to read request body", e);
                return false;
            }
        }

        // Generate expected signature using Authorization header as secret
        String raw = String.format("%s|%s|%s|%s|%s", method, uri, timestamp, nonce, body);

        // Use HMAC-SHA256 with Authorization header as the secret key
        String expectedSignature = Base64.getEncoder().encodeToString(
                HmacUtils.hmacSha256(authHeader, raw)
        );

        boolean isValid = expectedSignature.equals(signature);
        if (!isValid) {
            log.warn("Invalid signature for request to {}", uri);
        }

        return isValid;
    }

    /**
     * Validates if the timestamp is within acceptable time range
     * Prevents replay attacks by rejecting old requests
     */
    private boolean isTimestampValid(String timestamp) {
        try {
            long now = Instant.now().toEpochMilli();
            long requestTime = Long.parseLong(timestamp);
            boolean isValid = Math.abs(now - requestTime) <= TIMESTAMP_TOLERANCE_MS;

            if (!isValid) {
                log.warn("Timestamp too old or in future: {}", timestamp);
            }

            return isValid;
        } catch (NumberFormatException e) {
            log.warn("Invalid timestamp format: {}", timestamp);
            return false;
        }
    }
}