package com.gucardev.springbootstartertemplate.infrastructure.config.starter;


import com.gucardev.springbootstartertemplate.infrastructure.filter.pbkeyurl.RSAKeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StarterController {

    private final TimeZoneHelper timeZoneHelper;
    private final LoggingSettingsHelper loggingSettingsHelper;
    private final RSAKeyGenerator rsaKeyGenerator;
//    private final GitProperties gitProperties;
//    private final BuildProperties buildProperties;

    @GetMapping({"/", "/hello"})
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping({"/timezone", "/time"})
    public Map<String, String> getTimezone() {
        return timeZoneHelper.getCurrentTimeZoneInfo();
    }


    @GetMapping(value = "/public-key")
    public String getPublicKey() {
        // Convert the public key to PEM format
        String publicKeyBase64 = rsaKeyGenerator.getEncodedPublicKey();
        return "-----BEGIN PUBLIC KEY-----\n" +
                publicKeyBase64 +
                "\n-----END PUBLIC KEY-----";
    }

    @PutMapping("/log-level")
    public ResponseEntity<String> changeLogLevel(@RequestParam String level) {
        try {
            String newLevel = loggingSettingsHelper.changeLogLevel(level);
            return ResponseEntity.ok("Log level changed to: " + newLevel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid log level. Use: TRACE, DEBUG, INFO, WARN, ERROR");
        }
    }

    @GetMapping("/log-level")
    public ResponseEntity<String> getLogLevel() {
        return ResponseEntity.ok("Current log level: " + loggingSettingsHelper.getCurrentLogLevel());
    }

//    @GetMapping("/git-information")
//    public Map<String, InfoProperties> getGitProperties() {
//        return Map.of("gitProperties", gitProperties, "buildProperties", buildProperties);
//    }

}