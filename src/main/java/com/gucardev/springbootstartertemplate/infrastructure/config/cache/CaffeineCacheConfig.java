package com.gucardev.springbootstartertemplate.infrastructure.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
//@EnableCaching
public class CaffeineCacheConfig {

    @Value("${cache.short.ttl:60}")
    private Integer shortTtlSeconds;

    @Value("${cache.medium.ttl:120}")
    private Integer mediumTtlSeconds;

    @Value("${cache.default.max-size:1000}")
    private Integer defaultMaxSize;

    // Cache manager bean names
    public static final String CACHE_MANAGER_SHORT_LIVED = "shortLivedCacheManager";
    public static final String CACHE_MANAGER_MEDIUM_LIVED = "mediumLivedCacheManager";

    // Cache names
    public static final String CACHE_MACHINE_PRODUCTS = "machine_products";
    public static final String CACHE_USER_PREFERENCES = "user_preferences";

    // Lists of cache names managed by each cache manager
    private static final List<String> SHORT_LIVED_CACHES = Arrays.asList(
            "user_sessions",
            "temporary_tokens"
    );

    private static final List<String> MEDIUM_LIVED_CACHES = Arrays.asList(
            CACHE_MACHINE_PRODUCTS,
            CACHE_USER_PREFERENCES
    );

    @Primary
    @Bean(CACHE_MANAGER_SHORT_LIVED)
    public CacheManager shortLivedCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(SHORT_LIVED_CACHES);
        cacheManager.setCaffeine(buildCacheSpec(shortTtlSeconds, defaultMaxSize));
        return cacheManager;
    }

    @Bean(CACHE_MANAGER_MEDIUM_LIVED)
    public CacheManager mediumLivedCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(MEDIUM_LIVED_CACHES);
        cacheManager.setCaffeine(buildCacheSpec(mediumTtlSeconds, defaultMaxSize));
        return cacheManager;
    }

    private Caffeine<Object, Object> buildCacheSpec(Integer ttlSeconds, Integer maxSize) {
        return Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(ttlSeconds, TimeUnit.SECONDS)
//                .recordStats()
                ;
    }
}
