package com.gucardev.springbootstartertemplate.infrastructure.config.starter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoggingSettingsHelper {

    public String changeLogLevel(String level) {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        Level newLevel = Level.valueOf(level.toUpperCase());
        rootLogger.setLevel(newLevel);
        log.info("Log level changed to: {}", level.toUpperCase());
        return level.toUpperCase();
    }

    public String getCurrentLogLevel() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        return rootLogger.getLevel().toString();
    }
}
