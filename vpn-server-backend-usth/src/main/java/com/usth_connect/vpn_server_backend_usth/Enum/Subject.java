package com.usth_connect.vpn_server_backend_usth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Subject {
    CALCULUS("Calculus"),
    LINEAR_ALGEBRA("Linear Algebra"),
    CHEMISTRY("Chemistry"),
    PHYSICS("Physics"),
    PROGRAMMING("Programming"),
    ARTIFICIAL_INTELLIGENCE("Artificial Intelligence"),
    BIOLOGY("Biology"),
    PRACTICAL_LAB("Practical laboratory"),
    ENVIRONMENT("Environment"),
    HEALTH_CARE("Health Care"),
    ELECTRONIC("Electronic");

    private final String displayValue;

    Subject(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static Subject fromDisplayValue(String displayValue) {
        for (Subject subject : Subject.values()) {
            if (subject.getDisplayValue().equalsIgnoreCase(displayValue)) {
                return subject;
            }
        }
        throw new IllegalArgumentException("Unknown display value: " + displayValue);
    }
}
