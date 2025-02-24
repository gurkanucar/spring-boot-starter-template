package com.gucardev.springbootstartertemplate.infrastructure.constants;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static String DEFAULT_AUDITOR;
    public static String API_BASE_URL;
    public static String API_KEY;

    @Value("${constants.default-auditor}")
    private String defaultAuditor;

    @Value("${constants.api-url}")
    private String apiUrlProperty;

    @Value("${constants.api-key}")
    private String apiKeyProperty;

    @PostConstruct
    public void init() {
        DEFAULT_AUDITOR = defaultAuditor;
        API_BASE_URL = apiUrlProperty;
        API_KEY = apiKeyProperty;
    }
}