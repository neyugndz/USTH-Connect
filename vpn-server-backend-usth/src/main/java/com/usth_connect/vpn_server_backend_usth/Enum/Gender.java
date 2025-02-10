package com.usth_connect.vpn_server_backend_usth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    PREFER_NOT_TO_SAY("Prefer not to say");

    private final String displayValue;

    Gender(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static Gender fromDisplayValue(String displayValue) {
        for (Gender gender : Gender.values()) {
            if (gender.getDisplayValue().equalsIgnoreCase(displayValue)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown display value: " + displayValue);
    }
}
