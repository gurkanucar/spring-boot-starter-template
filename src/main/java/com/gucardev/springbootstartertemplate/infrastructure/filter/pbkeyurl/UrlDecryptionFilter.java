package com.gucardev.springbootstartertemplate.infrastructure.filter.pbkeyurl;

import com.gucardev.springbootstartertemplate.infrastructure.annotation.ExcludeFromAspect;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@ExcludeFromAspect
@Component
//@Order(1)
public class UrlDecryptionFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UrlDecryptionFilter.class);

    @Autowired
    private RSAKeyGenerator keyGenerator;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Only process requests under /api/secure
        if (path.startsWith("/api/secure")) {
            String encryptedParam = request.getParameter("val");

            // If there's no encrypted param, just proceed
            if (encryptedParam == null || encryptedParam.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                // URL decode and decrypt
                encryptedParam = URLDecoder.decode(encryptedParam, StandardCharsets.UTF_8);
                String decryptedUrl = encryptionUtil.decrypt(encryptedParam, keyGenerator.getPrivateKey());

                // Build the new request path and query from decrypted URI
                URI uri = URI.create(decryptedUrl);
                String realPath = uri.getPath();
                String query = uri.getQuery();

                DecryptedRequestWrapper wrapper = new DecryptedRequestWrapper(request, realPath, query);
                filterChain.doFilter(wrapper, response);

            } catch (Exception e) {
                logger.error("Error decrypting URL", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid encrypted URL");
            }

        } else {
            // Non-secure endpoints proceed as normal
            filterChain.doFilter(request, response);
        }
    }

    private static class DecryptedRequestWrapper extends HttpServletRequestWrapper {
        private final String path;
        private final String queryString;

        public DecryptedRequestWrapper(HttpServletRequest request, String path, String queryString) {
            super(request);
            this.path = path;
            this.queryString = queryString;
        }

        @Override
        public String getRequestURI() {
            return path;
        }

        @Override
        public String getServletPath() {
            return path;
        }

        @Override
        public String getQueryString() {
            return queryString;
        }

        @Override
        public String getParameter(String name) {
            // Parse our decrypted query string first
            if (queryString != null) {
                String[] params = queryString.split("&");
                for (String param : params) {
                    String[] pair = param.split("=");
                    if (pair.length == 2 && pair[0].equals(name)) {
                        return pair[1];
                    }
                }
            }
            // Fallback to the original request parameters
            return super.getParameter(name);
        }
    }
}
