package com.gucardev.springbootstartertemplate.infrastructure.config.security;

import com.gucardev.springbootstartertemplate.infrastructure.annotation.ExcludeFromAspect;
import com.gucardev.springbootstartertemplate.infrastructure.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;


@ExcludeFromAspect
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    //    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = extractToken(request);

            if (jwt != null && tokenService.validateToken(jwt)) {
                String username = tokenService.extractUsername(jwt);
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                var userDetails = getMockUserDetails(username);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            logger.error("Authentication error: ", e);
            // Don't send error response for missing tokens, only for invalid ones
            if (!(e instanceof IllegalArgumentException && e.getMessage().contains("No JWT token found"))) {
                sendErrorResponse(response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    private String extractToken(HttpServletRequest request) {
        // First check for Authorization header
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        // For WebSocket connections where we can't set custom headers
        String token = request.getParameter("token");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        // No token found in either location
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
    }

    private static UserDetails getMockUserDetails(String username) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}


