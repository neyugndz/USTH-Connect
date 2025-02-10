package com.usth_connect.vpn_server_backend_usth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Opponent {
    CHIT_CHAT("Chit Chatting"),
    SHARE_KNOWLEDGE("Share knowledge"),
    STUDY_SUPPORTER("Study supporter");

    private final String displayValue;

    Opponent(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static Opponent fromDisplayValue(String displayValue) {
        for (Opponent opponent : Opponent.values()) {
            if (opponent.getDisplayValue().equalsIgnoreCase(displayValue)) {
                return opponent;
            }
        }
        throw new IllegalArgumentException("Unknown display value: " + displayValue);
    }
}
