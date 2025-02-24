package com.gucardev.springbootstartertemplate.infrastructure.config.firebase.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public  class MulticastNotificationRequest {
    private String title;
    private String body;
    private List<String> recipientTokens;
    private Map<String, String> data = new HashMap<>();
}
