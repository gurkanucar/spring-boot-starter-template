package com.gucardev.springbootstartertemplate.infrastructure.filter;

import com.gucardev.springbootstartertemplate.infrastructure.annotation.ExcludeFromAspect;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ExcludeFromAspect
@Component
@Slf4j
@Order(1)
public class LoggerOncePerRequestFilter extends OncePerRequestFilter {

  private static final String[] IGNORED_ENDPOINTS = {"/download", "/health", "/status"};

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    // Wrap the request and response for content caching
    ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(
        response);

    // Start measuring the time taken
    long startTime = System.currentTimeMillis();

    try {
      filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
    } finally {
      // Measure elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;

      // Log only if debug mode and not an ignored endpoint
      if (log.isDebugEnabled() && !shouldNotFilter(request)) {
        logRequestAndResponse(cachingRequestWrapper, cachingResponseWrapper, elapsedTime);
      }

      // Copy the response body back to the original response
      cachingResponseWrapper.copyBodyToResponse();
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String requestUri = request.getRequestURI();
    for (String endpoint : IGNORED_ENDPOINTS) {
      if (requestUri.startsWith(endpoint)) {
        log.debug("Skipping filter for URI: {}", requestUri);
        return true;
      }
    }
    return false;
  }

  private void logRequestAndResponse(ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response,
      long elapsedTime) {
    String requestBody = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
    String responseBody = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
    String clientIp = getClientIpAddress(request);

    log.debug("Request and Response Log: \n" +
            "=== Request ===\n" +
            "Client IP: {}\n" +
            "Method: {}\n" +
            "URI: {}\n" +
            "Query Params: {}\n" +
            "Headers: {}\n" +
            "Body: {}\n" +
            "=== Response ===\n" +
            "Status: {}\n" +
            "Headers: {}\n" +
            "Body: {}\n" +
            "Time Taken: {} ms\n",
        clientIp,
        request.getMethod(),
        request.getRequestURI(),
        request.getQueryString(),
        getRequestHeaders(request),
        requestBody,
        response.getStatus(),
        getResponseHeaders(response),
        responseBody,
        elapsedTime
    );
  }

  private String getClientIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  private String getRequestHeaders(HttpServletRequest request) {
    StringBuilder headers = new StringBuilder();
    request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
      headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("; ");
    });
    return headers.toString();
  }

  private String getResponseHeaders(HttpServletResponse response) {
    StringBuilder headers = new StringBuilder();
    response.getHeaderNames().forEach(headerName -> {
      headers.append(headerName).append(": ").append(response.getHeader(headerName)).append("; ");
    });
    return headers.toString();
  }
}
