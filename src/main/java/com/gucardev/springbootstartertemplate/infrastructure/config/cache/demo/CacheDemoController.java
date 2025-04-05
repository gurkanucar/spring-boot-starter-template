package com.gucardev.springbootstartertemplate.infrastructure.config.cache.demo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache-demo")
@RequiredArgsConstructor
@Slf4j
public class CacheDemoController {

    private final CacheDemoService cacheDemoService;

    @GetMapping("/product/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable String id) {
        log.info("Request received for product ID: {}", id);
        long startTime = System.currentTimeMillis();

        String result = cacheDemoService.getProductData(id);

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed for product ID: {} in {}ms", id, endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-preferences/{userId}")
    public ResponseEntity<Map<String, Object>> getUserPreferences(@PathVariable String userId) {
        log.info("Request received for user preferences, userId: {}", userId);
        long startTime = System.currentTimeMillis();

        String result = cacheDemoService.getUserPreferences(userId);

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed for user preferences, userId: {} in {}ms", userId, endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Map<String, Object>> getSession(@PathVariable String sessionId) {
        log.info("Request received for session data, sessionId: {}", sessionId);
        long startTime = System.currentTimeMillis();

        String result = cacheDemoService.getSessionData(sessionId);

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed for session data, sessionId: {} in {}ms", sessionId, endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/session/{sessionId}")
    public ResponseEntity<Map<String, Object>> updateSession(
            @PathVariable String sessionId,
            @RequestParam String data) {
        log.info("Request received to update session, sessionId: {}", sessionId);
        long startTime = System.currentTimeMillis();

        String result = cacheDemoService.updateSession(sessionId, data);

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed to update session, sessionId: {} in {}ms", sessionId, endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/product/{id}/cache")
    public ResponseEntity<Map<String, Object>> invalidateProductCache(@PathVariable String id) {
        log.info("Request received to invalidate product cache, ID: {}", id);
        long startTime = System.currentTimeMillis();

        cacheDemoService.invalidateProductCache(id);

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cache invalidated for product " + id);
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed to invalidate product cache, ID: {} in {}ms", id, endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logoutUser(
            @RequestParam String userId,
            @RequestParam String sessionId) {
        log.info("Request received for user logout, userId: {}, sessionId: {}", userId, sessionId);
        long startTime = System.currentTimeMillis();

        cacheDemoService.logoutUser(userId, sessionId);

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User logged out and caches cleared");
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed for user logout in {}ms", endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/tokens/clear")
    public ResponseEntity<Map<String, Object>> clearAllTokens() {
        log.info("Request received to clear all token caches");
        long startTime = System.currentTimeMillis();

        cacheDemoService.clearAllTokens();

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "All token caches cleared");
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed to clear all token caches in {}ms", endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cache/clear-all")
    public ResponseEntity<Map<String, Object>> clearAllCaches() {
        log.info("Request received to clear ALL caches");
        long startTime = System.currentTimeMillis();

        cacheDemoService.clearAllCaches();

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "All caches cleared");
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed to clear ALL caches in {}ms", endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/token/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestParam String token) {
        log.info("Request received to validate token: {}", token);
        long startTime = System.currentTimeMillis();

        String result = cacheDemoService.validateToken(token);

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed to validate token in {}ms", endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/token/generate")
    public ResponseEntity<Map<String, Object>> generateToken() {
        log.info("Request received to generate token");
        long startTime = System.currentTimeMillis();

        String result = cacheDemoService.generateToken();

        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("token", result);
        response.put("executionTimeMs", endTime - startTime);

        log.info("Request completed to generate token in {}ms", endTime - startTime);
        return ResponseEntity.ok(response);
    }
}