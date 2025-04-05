package com.gucardev.springbootstartertemplate.infrastructure.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.stream.Collectors;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HmacAuthService {
    private static final long TIMESTAMP_TOLERANCE_MS = 5_000; // 5 seconds tolerance
    private final ObjectMapper objectMapper;

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

        // Get the combined X-App-Signature header
        String combinedAuthHeader = request.getHeader("X-App-Signature");
        if (combinedAuthHeader == null || combinedAuthHeader.isEmpty()) {
            log.warn("Missing X-App-Signature header for HMAC authentication");
            return false;
        }

        try {
            // Decode Base64
            byte[] decodedBytes = Base64.getDecoder().decode(combinedAuthHeader);
            String decodedJson = new String(decodedBytes);

            // Parse JSON to Map
            var authComponents = objectMapper.readValue(decodedJson, Map.class);

            String timestamp = authComponents.get("timestamp").toString();
            String nonce = authComponents.get("nonce").toString();
            String providedSignature = authComponents.get("signature").toString();

            // Validate required components
            if (timestamp == null || nonce == null || providedSignature == null) {
                log.warn("Missing required components in X-App-Signature header");
                return false;
            }

            // Validate timestamp
            if (!isTimestampValid(timestamp)) {
                log.warn("Invalid timestamp: {}", timestamp);
                return false;
            }

            String method = request.getMethod();
            String uri = request.getRequestURI();

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

            boolean isValid = expectedSignature.equals(providedSignature);
            if (!isValid) {
                log.warn("Invalid signature for request to {}", uri);
            }

            return isValid;

        } catch (Exception e) {
            log.error("Error validating HMAC signature", e);
            return false;
        }
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