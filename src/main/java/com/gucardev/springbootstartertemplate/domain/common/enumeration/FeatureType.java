package com.gucardev.springbootstartertemplate.domain.common.enumeration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum FeatureType {

    BASIC(0, "Basic Feature"),
    PREMIUM(1, "Premium Feature"),
    ENTERPRISE(2, "Enterprise Feature"),
    CUSTOM(3, "Custom Feature"),
    UNKNOWN(99, "Unknown Feature");

    private static final Map<Integer, FeatureType> FEATURES_BY_CODE = new HashMap<>();

    static {
        for (FeatureType feature : values()) {
            FEATURES_BY_CODE.put(feature.code, feature);
        }
    }

    private final int code;
    private final String label;

    FeatureType(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static FeatureType fromCode(int code) {
        return FEATURES_BY_CODE.getOrDefault(code, UNKNOWN);
    }

    public static FeatureType fromInt(int value) {
        return fromCode(value);
    }

}