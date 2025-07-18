package com.br.sysgateway.dto;

import com.br.sysgateway.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
public record TransactionResponseDTO(
        PaymentType paymentType,
        String status,
        String description,
        Long transactionId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime dataTransaction,
        BigDecimal valueTransaction,
        String base64Image,
        String preferenceId
){
}
