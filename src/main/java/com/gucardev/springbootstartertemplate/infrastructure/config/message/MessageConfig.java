package com.gucardev.springbootstartertemplate.infrastructure.config.message;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String acceptLanguageHeader = request.getHeader("Accept-Language");
                return acceptLanguageHeader != null
                        ? Locale.forLanguageTag(acceptLanguageHeader)
                        : Locale.getDefault();
            }
        };
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }
}
