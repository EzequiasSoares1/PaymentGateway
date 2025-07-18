package com.gateway.paymentgateway.dto;

import com.gateway.paymentgateway.enums.ProviderType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
public class PaymentResponseDTO {
    private Long externalId;
    private String paymentType;
    private String status;
    private BigDecimal amount;
    private String currency;
    private String description;
    private LocalDateTime transactionDate;
    private ProviderType paymentProvider;
    private String base64Image;
    private String preferenceId;
}
