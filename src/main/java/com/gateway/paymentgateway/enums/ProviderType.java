package com.gateway.paymentgateway.enums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProviderType {
    MERCADOPAGO, PAGSEGURO;

    @JsonCreator
    public static ProviderType fromString(String value) {
        switch (value) {
            case "MERCADOPAGO":
                return MERCADOPAGO;
            case "PAGSEGURO":
                return PAGSEGURO;
            default:
                throw new IllegalArgumentException("Unknown provider type: " + value);
        }
    }

    @JsonValue
    public String toJson() {
        switch (this) {
            case MERCADOPAGO:
                return "MERCADOPAGO";
            case PAGSEGURO:
                return "PAGSEGURO";
            default:
                throw new IllegalArgumentException("Unknown provider type: " + this);
        }
    }
}
