package com.usth_connect.vpn_server_backend_usth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StudyTime {
    EARLY_MORNING("Early Morning"),
    MIDDAY("Midday"),
    EVENING("Evening"),
    NIGHT("Night"),
    MIDNIGHT("Midnight"),
    OVERNIGHT("Overnight");

    private final String displayValue;

    StudyTime(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static StudyTime fromDisplayValue(String displayValue) {
        for (StudyTime studyTime : StudyTime.values()) {
            if (studyTime.getDisplayValue().equalsIgnoreCase(displayValue)) {
                return studyTime;
            }
        }
        throw new IllegalArgumentException("Unknown display value: " + displayValue);
    }
}

