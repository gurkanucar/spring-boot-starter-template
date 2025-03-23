package com.gucardev.springbootstartertemplate.infrastructure.config.ratelimiter;

import com.gucardev.springbootstartertemplate.infrastructure.exception.ExceptionMessage;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.gucardev.springbootstartertemplate.infrastructure.exception.helper.ExceptionUtil.buildException;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimiterConfiguration rateLimiterConfiguration;

    @Around("@annotation(rateLimitByUser)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimitByUser rateLimitByUser) throws Throwable {
        // Get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Rate limit attempted for unauthenticated request");
            throw buildException(ExceptionMessage.FORBIDDEN_EXCEPTION);
        }

        String username = authentication.getName();
        log.debug("Applying rate limit for user: {}", username);

        try {
            // Get rate limiter for this user
            return rateLimiterConfiguration.getRateLimiterForUser(username)
                    .executeCheckedSupplier(joinPoint::proceed);
        } catch (RequestNotPermitted e) {
            log.warn("Rate limit exceeded for user: {}", username);
            throw buildException(
                    ExceptionMessage.TOO_MANY_REQUESTS_EXCEPTION
            );
        }
    }
}
