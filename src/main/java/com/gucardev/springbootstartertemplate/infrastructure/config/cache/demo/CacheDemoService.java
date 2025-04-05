package com.gucardev.springbootstartertemplate.infrastructure.config.cache.demo;

import com.gucardev.springbootstartertemplate.infrastructure.config.cache.CacheNames;
import com.gucardev.springbootstartertemplate.infrastructure.config.cache.CaffeineCacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class CacheDemoService {

    // Simulate database with a simple map
    private final Map<String, Object> mockDatabase = new HashMap<>();

    /**
     * Basic caching example using short-lived cache (default)
     */
    @Cacheable(value = CacheNames.CACHE_PRODUCTS, key = "#id")
    public String getProductData(String id) {
        log.info("Cache MISS for product ID: {} - This would be a database call", id);
        simulateSlowOperation();
        return "Product data for " + id + " at " + System.currentTimeMillis();
    }

    /**
     * Caching example with medium-lived cache manager
     */
    @Cacheable(
            value = CacheNames.CACHE_USER_PREFERENCES,
            key = "#userId",
            cacheManager = CaffeineCacheConfig.CACHE_MANAGER_MEDIUM_LIVED
    )
    public String getUserPreferences(String userId) {
        log.info("Cache MISS for user preferences, userId: {} - This would be a database call", userId);
        simulateSlowOperation();
        return "User preferences for " + userId + " at " + System.currentTimeMillis();
    }

    /**
     * Cache update example using CachePut
     */
    @CachePut(value = CacheNames.CACHE_USER_SESSIONS, key = "#sessionId")
    public String updateSession(String sessionId, String data) {
        log.info("Updating session data for sessionId: {}", sessionId);
        String sessionData = data + " (updated at " + System.currentTimeMillis() + ")";
        mockDatabase.put(sessionId, sessionData);
        return sessionData;
    }

    /**
     * Retrieve session data with caching
     */
    @Cacheable(value = CacheNames.CACHE_USER_SESSIONS, key = "#sessionId")
    public String getSessionData(String sessionId) {
        log.info("Cache MISS for session data, sessionId: {} - This would be a database call", sessionId);
        simulateSlowOperation();
        return mockDatabase.getOrDefault(sessionId, "No session data found").toString();
    }

    /**
     * Cache eviction example - remove specific entry
     */
    @CacheEvict(value = CacheNames.CACHE_PRODUCTS, key = "#id")
    public void invalidateProductCache(String id) {
        log.info("Invalidating cache for product ID: {}", id);
    }

    /**
     * Multiple cache operations example
     */
    @Caching(evict = {
            @CacheEvict(value = CacheNames.CACHE_USER_PREFERENCES, key = "#userId"),
            @CacheEvict(value = CacheNames.CACHE_USER_SESSIONS, key = "#sessionId")
    })
    public void logoutUser(String userId, String sessionId) {
        log.info("User logout - clearing user-related caches for userId: {}, sessionId: {}", userId, sessionId);
        // Perform actual logout operations
    }

    /**
     * Clear all entries from a cache
     */
    @CacheEvict(value = CacheNames.CACHE_TEMPORARY_TOKENS, allEntries = true)
    public void clearAllTokens() {
        log.info("Clearing all temporary token cache entries");
    }

    /**
     * Clear all entries from multiple caches
     */
    @Caching(evict = {
            @CacheEvict(value = CacheNames.CACHE_PRODUCTS, allEntries = true),
            @CacheEvict(value = CacheNames.CACHE_USER_PREFERENCES, allEntries = true),
            @CacheEvict(value = CacheNames.CACHE_USER_SESSIONS, allEntries = true),
            @CacheEvict(value = CacheNames.CACHE_TEMPORARY_TOKENS, allEntries = true)
    })
    public void clearAllCaches() {
        log.info("Clearing ALL cache entries from all caches");
    }

    /**
     * Conditional caching example
     */
    @Cacheable(
            value = CacheNames.CACHE_TEMPORARY_TOKENS,
            key = "#token",
            condition = "#token.length() > 10"
    )
    public String validateToken(String token) {
        log.info("Cache MISS for token validation, token: {} - This would be a database call", token);
        simulateSlowOperation();
        return "Token " + token + " is " + (token.length() > 15 ? "valid" : "invalid");
    }

    /**
     * Generate a random token for testing
     */
    public String generateToken() {
        String token = UUID.randomUUID().toString();
        log.info("Generated new token: {}", token);
        return token;
    }

    /**
     * Helper method to simulate slow database operations
     */
    private void simulateSlowOperation() {
        try {
            // Simulate database latency
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}