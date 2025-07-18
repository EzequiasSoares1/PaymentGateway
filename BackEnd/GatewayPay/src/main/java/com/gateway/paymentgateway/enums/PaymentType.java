package com.gateway.paymentgateway.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentType {
    CARD,PIX,CREATE_PREFERENCE;

    @JsonCreator
    public static PaymentType fromString(String value) {
        return switch (value) {
            case "CARD" -> CARD;
            case "PIX" -> PIX;
            case "CREATE_PREFERENCE" -> CREATE_PREFERENCE;
            default -> throw new IllegalArgumentException("Unknown payment type: " + value);
        };
    }

    @JsonValue
    public String toJson() {
        return switch (this) {
            case CARD -> "CARD";
            case PIX -> "PIX";
            default -> throw new IllegalArgumentException("Unknown payment type: " + this);
        };
    }
}
