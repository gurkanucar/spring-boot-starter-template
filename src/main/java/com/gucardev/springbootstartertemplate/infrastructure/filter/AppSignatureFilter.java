//package com.gucardev.springbootstartertemplate.infrastructure.filter;
//
//import com.gucardev.springbootstartertemplate.infrastructure.annotation.ExcludeFromAspect;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
//@ExcludeFromAspect
//@Component
//@RequiredArgsConstructor
//public class AppSignatureFilter extends OncePerRequestFilter {
//
//    private final HmacAuthService hmacAuthService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//
//        // Skip for OPTIONS requests
//        if (request.getMethod().equals("OPTIONS")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Skip if no Authorization header is present - this will also skip HMAC validation in the service
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || authHeader.isEmpty()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Validate HMAC signature if Authorization header is present
//        if (!hmacAuthService.isValidSignature(request)) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid HMAC Signature");
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}