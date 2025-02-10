package com.usth_connect.vpn_server_backend_usth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StudyPlace {
    CAFE("Café"),
    HOME("Home"),
    LIBRARY("Library"),
    BOOKSTORE("BookStore"),
    ONLINE("Online");

    private final String displayValue;

    StudyPlace(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static StudyPlace fromDisplayValue(String displayValue) {
        for (StudyPlace studyPlace : StudyPlace.values()) {
            if (studyPlace.getDisplayValue().equalsIgnoreCase(displayValue)) {
                return studyPlace;
            }
        }
        throw new IllegalArgumentException("Unknown display value: " + displayValue);
    }
}

