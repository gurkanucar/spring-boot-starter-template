package com.gucardev.springbootstartertemplate.infrastructure.filter;

import com.gucardev.springbootstartertemplate.infrastructure.annotation.ExcludeFromAspect;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@ExcludeFromAspect
@Order(2)
public class TraceIdFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String TRACE_ID_MDC_KEY = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.addHeader(TRACE_ID_HEADER, MDC.get(TRACE_ID_MDC_KEY));
        filterChain.doFilter(request, response);
    }
}
