package com.gucardev.springbootstartertemplate.infrastructure.filter.pbkeyurl;


import com.gucardev.springbootstartertemplate.infrastructure.annotation.ExcludeFromAspect;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
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
@Order(1)
public class UrlDecryptionFilter extends OncePerRequestFilter {

    @Autowired
    private RSAKeyGenerator keyGenerator;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Only process secure endpoints
        if (path.startsWith("/api/secure")) {
            try {
                // Get encrypted data from the 'data' parameter
                String encryptedData = request.getParameter("val");

                if (encryptedData != null) {
                    // URL decode the encrypted data
                    encryptedData = URLDecoder.decode(encryptedData, StandardCharsets.UTF_8);

                    // Decrypt the data using the private key
                    String decryptedUrl = encryptionUtil.decrypt(encryptedData, keyGenerator.getPrivateKey());

                    // Parse the decrypted URL to get the real path and parameters
                    URI uri = URI.create(decryptedUrl);
                    String realPath = uri.getPath();
                    String query = uri.getQuery();

                    // Create a wrapper request with the decrypted URL
                    DecryptedRequestWrapper wrapper = new DecryptedRequestWrapper(request, realPath, query);

                    // Log for debugging
                    System.out.println("Decrypted URL: " + decryptedUrl);

                    // Continue with the modified request
                    filterChain.doFilter(wrapper, response);
                    return;
                }
            } catch (Exception e) {
                System.err.println("Error decrypting URL: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid encrypted URL");
                return;
            }
        }

        // For non-secure paths, continue normally
        filterChain.doFilter(request, response);
    }

    // Custom request wrapper to change the request path and parameters
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
            // Parse the query string to get parameters
            if (queryString != null) {
                String[] params = queryString.split("&");
                for (String param : params) {
                    String[] pair = param.split("=");
                    if (pair.length == 2 && pair[0].equals(name)) {
                        return pair[1];
                    }
                }
            }

            // Fall back to original parameters for any that aren't in our query string
            return super.getParameter(name);
        }
    }
}