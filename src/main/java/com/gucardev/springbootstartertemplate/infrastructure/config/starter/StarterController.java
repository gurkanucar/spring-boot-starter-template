package com.gucardev.springbootstartertemplate.infrastructure.config.starter;


import com.gucardev.springbootstartertemplate.infrastructure.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StarterController {

    private final TimeZoneHelper timeZoneHelper;
    private final LoggingSettingsHelper loggingSettingsHelper;
//    private final GitProperties gitProperties;
//    private final BuildProperties buildProperties;

    @GetMapping({"/", "/hello"})
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping({"/timezone", "/time"})
    public ResponseEntity<SuccessResponse<Object>> getTimezone() {
        return ResponseEntity.ok(
                SuccessResponse.builder()
                        .status(HttpStatus.OK)
                        .messageKey("response.user.created")
                        .data(timeZoneHelper.getCurrentTimeZoneInfo())
                        .build()
        );
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