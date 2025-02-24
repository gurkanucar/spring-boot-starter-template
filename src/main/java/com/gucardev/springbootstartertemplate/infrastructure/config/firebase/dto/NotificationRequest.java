package com.gucardev.springbootstartertemplate.infrastructure.config.firebase.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public  class NotificationRequest {
    private String title;
    private String body;
    private String recipientToken;
    private Map<String, String> data = new HashMap<>();
}
