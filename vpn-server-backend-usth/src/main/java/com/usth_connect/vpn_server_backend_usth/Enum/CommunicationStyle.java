package com.usth_connect.vpn_server_backend_usth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CommunicationStyle {
    VIDEO_CALL("Video call"),
    PHONE_CALL("Phone call"),
    TEXT_MESSAGE("Text message"),
    IN_PERSON("In-person (face-to-face)");

    private final String displayValue;

    CommunicationStyle(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static CommunicationStyle fromValue(String value) {
        for (CommunicationStyle style : values()) {
            if (style.getDisplayValue().equalsIgnoreCase(value)) {
                return style;
            }
        }

        throw new IllegalArgumentException("Unknown display value: " + value);
    }
}

