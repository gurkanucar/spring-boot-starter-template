package com.gucardev.springbootstartertemplate.infrastructure.filter;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HmacAuthService {

    private static final String SECRET = "my_shared_secret"; // Ortak secret (client + server)

    /**
     * İstekten gelen imzayı kontrol eder.
     */
    public boolean isValidSignature(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String timestamp = request.getHeader("X-App-Timestamp");
        String signature = request.getHeader("X-App-Signature");

        if (timestamp == null || signature == null) return false;
        if (!isTimestampValid(timestamp)) return false;

        String body = ""; // GET için boş; POST için payload alınabilir
        String raw = String.format("%s|%s|%s|%s|%s", method, uri, timestamp, body, SECRET);
        String expectedSignature = DigestUtils.sha256Hex(raw);

        return expectedSignature.equals(signature);
    }

    /**
     * Timestamp'in geçerli (yani yakın zamanlı) olup olmadığını kontrol eder.
     */
    private boolean isTimestampValid(String timestamp) {
        try {
            long now = Instant.now().toEpochMilli();
            long req = Long.parseLong(timestamp);
            return Math.abs(now - req) <= 30_000; // 30 saniyelik tolerans
        } catch (Exception e) {
            return false;
        }
    }
}
