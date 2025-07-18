package com.gateway.paymentgateway.enums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProviderType {
    MERCADOPAGO, STRIPE;

    @JsonCreator
    public static ProviderType fromString(String value) {
        return switch (value) {
            case "MERCADOPAGO" -> MERCADOPAGO;
            case "STRIPE" -> STRIPE;
            default -> throw new IllegalArgumentException("Unknown provider type: " + value);
        };
    }

    @JsonValue
    public String toJson() {
        return switch (this) {
            case MERCADOPAGO -> "MERCADOPAGO";
            case STRIPE -> "STRIPE";
            default -> throw new IllegalArgumentException("Unknown provider type: " + this);
        };
    }
}
