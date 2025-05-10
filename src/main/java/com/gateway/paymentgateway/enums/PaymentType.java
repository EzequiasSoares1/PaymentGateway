package com.gateway.paymentgateway.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentType {
    CARD_PAYMENT,PIX,CREATE_PREFERENCE;

    @JsonCreator
    public static PaymentType fromString(String value) {
        switch (value) {
            case "CARD_PAYMENT":
                return CARD_PAYMENT;
            case "PIX":
                return PIX;
            case "CREATE_PREFERENCE":
                return CREATE_PREFERENCE;
            default:
                throw new IllegalArgumentException("Unknown payment type: " + value);
        }
    }

    @JsonValue
    public String toJson() {
        switch (this) {
            case CARD_PAYMENT:
                return "CARD_PAYMENT";
            case PIX:
                return "PIX";
            default:
                throw new IllegalArgumentException("Unknown payment type: " + this);
        }
    }
}
