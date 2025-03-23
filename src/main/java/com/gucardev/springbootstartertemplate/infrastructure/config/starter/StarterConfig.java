package com.gucardev.springbootstartertemplate.infrastructure.config.starter;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class StarterConfig implements CommandLineRunner {

    private final Environment environment;
    private final TimeZoneHelper timeZoneHelper;
    private final LoggingSettingsHelper loggingSettingsHelper;

    // Application Startup Logging
    @Override
    public void run(String... args) {
        logApplicationStartup();
    }

    private void logApplicationStartup() {
        String protocol = Optional.ofNullable(environment.getProperty("server.ssl.key-store"))
                .map(key -> "https")
                .orElse("http");
        String applicationName = environment.getProperty("spring.application.name", "Application");
        String serverPort = environment.getProperty("server.port", "8080");
        String contextPath = Optional.ofNullable(environment.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        // Get logging levels
        String loggingInfo = String.format("Root Log Level: %s", loggingSettingsHelper.getCurrentLogLevel());

        log.info(
                """
                        
                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\t{}://localhost:{}{}
                        \tExternal: \t{}://{}:{}{}
                        \tProfile(s): \t{}
                        \t{}
                        \t{}
                        ----------------------------------------------------------""",
                applicationName,
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                environment.getActiveProfiles().length == 0
                        ? environment.getDefaultProfiles()
                        : environment.getActiveProfiles(),
                timeZoneHelper.getFormattedTimezoneInfo(),
                loggingInfo
        );
    }
}
