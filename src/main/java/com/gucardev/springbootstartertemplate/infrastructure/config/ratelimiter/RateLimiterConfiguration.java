package com.gucardev.springbootstartertemplate.infrastructure.config.ratelimiter;


import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimiterConfiguration {

    // In-memory store for user-specific rate limiters
    private final Map<String, RateLimiter> userRateLimiters = new ConcurrentHashMap<>();

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        // Define default rate limiter configuration
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMinutes(1))  // Refresh limit every 1 minute
                .limitForPeriod(3)                          // Allow 3 calls per period
                .timeoutDuration(Duration.ofMillis(100))    // Thread waits for permission for 100ms
                .build();

        return RateLimiterRegistry.of(config);
    }

    public RateLimiter getRateLimiterForUser(String username) {
        return userRateLimiters.computeIfAbsent(username,
                name -> rateLimiterRegistry().rateLimiter(name));
    }
}


