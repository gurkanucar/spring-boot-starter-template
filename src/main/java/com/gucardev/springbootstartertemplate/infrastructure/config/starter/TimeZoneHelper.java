package com.gucardev.springbootstartertemplate.infrastructure.config.starter;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class TimeZoneHelper {

    public Map<String, String> getCurrentTimeZoneInfo() {
        Map<String, String> response = new HashMap<>();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        response.put("timezone", zoneId.getId());
        response.put("offset", zoneId.getRules().getOffset(now.toInstant()).getId());
        response.put("currentTime", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return response;
    }

    public String getFormattedTimezoneInfo() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        return String.format("Timezone: %s (%s) - Current Time: %s",
                zoneId.getId(),
                zoneId.getRules().getOffset(now.toInstant()).getId(),
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}