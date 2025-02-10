package com.usth_connect.vpn_server_backend_usth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Interest {
    V_POP("V-pop"),
    K_POP("K-pop"),
    EDM("EDM"),
    ART("Art"),
    POLITICS("Politics"),
    VLOGGING("Vlogging"),
    BASEBALL("Baseball"),
    FOOTBALL("Football"),
    VOLLEYBALL("Volleyball"),
    TABLE_TENNIS("Table tennis"),
    BASKETBALL("Basketball"),
    SKIING("Skiing"),
    SWIMMING("Swimming"),
    SKATEBOARDING("Skateboarding"),
    BOARD_GAMES("Board games"),
    DIY("DIY"),
    COOKING("Cooking"),
    PAINTING("Painting"),
    BOWLING("Bowling"),
    BALLET("Ballet"),
    DANCING("Dancing"),
    SINGING("Singing"),
    YOGA("Yoga"),
    JOGGING("Jogging"),
    ANIME("Anime"),
    FOOD_TOUR("Food tour"),
    TRAVEL("Travel"),
    STUDY("Study"),
    GYM("Gym"),
    KOREAN_FOOD("Korean food"),
    COFFEE("Coffee"),
    TEA("Tea"),
    BADMINTON("Badminton"),
    HIPHOP("Hiphop"),
    INVESTMENT("Investment"),
    ALCOHOLIC("Alcoholic"),
    KARATE("Karate"),
    TAEKWONDO("Taekwondo"),
    RAP("Rap"),
    MAKEUP("Makeup"),
    MOVIE("Movie"),
    NETFLIX("Netflix"),
    ICE_CREAM("Ice cream"),
    ESPORT("Esport");

    private final String displayValue;

    Interest(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static Interest fromDisplayValue(String displayValue) {
        if (displayValue != null) {
            displayValue = displayValue.trim(); // Trim any extra spaces
        }
        for (Interest interest : Interest.values()) {
            if (interest.getDisplayValue().equalsIgnoreCase(displayValue)) {
                return interest;
            }
        }
        throw new IllegalArgumentException("Unknown display value: " + displayValue);
    }
}

