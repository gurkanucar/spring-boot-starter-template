package com.gucardev.springbootstartertemplate.infrastructure.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//            .allowedOrigins("http://localhost:3000", "https://example.com")  // list your domains here
                .allowedOriginPatterns("*") // Allow all origins
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
