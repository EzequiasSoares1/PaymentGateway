package com.gateway.paymentgateway.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardPaymentDTO {
        @NotNull
        private String token;

        @NotNull
        private String issuerId;

        @NotNull
        private String paymentMethodId;

        @NotNull
        private BigDecimal transactionAmount;

        @NotNull
        private Integer installments;
}
