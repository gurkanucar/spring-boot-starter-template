package com.gucardev.springbootstartertemplate.infrastructure.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${app.jwt.secret-key}")
    private String secretKey;

    @Value("${app.jwt.validity-in-minutes}")
    private Integer expiration;

    private static final Duration ETERNAL_VALIDITY = Duration.ofDays(9999); // Practically eternal

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate time-limited token
    public String generateToken(String username, Map<String, Object> claims) {
        return buildToken(username, claims,
                LocalDateTime.now().plusMinutes(expiration));
    }

    // Generate "eternal" token
    public String generateEternalToken(String username, Map<String, Object> claims) {
        return buildToken(username, claims,
                LocalDateTime.now().plus(ETERNAL_VALIDITY));
    }

    private String buildToken(String username, Map<String, Object> claims, LocalDateTime expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSigningKey())
                .compact();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            // This will check signature and parsing
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            // Additionally check for expiration
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    // Usage example method
    public String generateTokenWithClaims(String username, String role, String... permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("permissions", Arrays.asList(permissions));
        return generateToken(username, claims);
    }
}