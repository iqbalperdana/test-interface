package com.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PlatformName {

    ZOHO("zoho"),
    ZENDESK("zendesk"),
    SALESFORCE("salesforce");

    private final String value;

    PlatformName(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PlatformName fromValue(String value) {
        for (PlatformName p : PlatformName.values()) {
            if (p.value.equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown platform: " + value);
    }

    public String getValue() {
        return value;
    }
}

