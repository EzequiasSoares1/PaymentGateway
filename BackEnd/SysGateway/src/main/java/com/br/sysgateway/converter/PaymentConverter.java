package com.br.sysgateway.converter;
import com.br.sysgateway.dto.PaymentResponseDTO;
import com.br.sysgateway.dto.TransactionResponseDTO;
import com.br.sysgateway.model.Payment;

public class PaymentConverter {

    public static TransactionResponseDTO toTransactionResponseDTO(PaymentResponseDTO dto) {
        return  TransactionResponseDTO.builder()
                .status(dto.getStatus())
                .description(dto.getDescription())
                .transactionId(dto.getExternalId() != null ? dto.getExternalId() : 0L)
                .dataTransaction(dto.getTransactionDate())
                .valueTransaction(dto.getAmount())
                .preferenceId(dto.getPreferenceId())
                .base64Image(dto.getBase64Image())
                .paymentType(dto.getPaymentType())
                .build();
    }

    public static Payment toEntity(PaymentResponseDTO dto) {
        if (dto == null) return null;

        return Payment.builder()
                .externalId(dto.getExternalId())
                .paymentType(dto.getPaymentType())
                .status(dto.getStatus())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .description(dto.getDescription())
                .transactionDate(dto.getTransactionDate())
                .paymentProvider(dto.getPaymentProvider())
                .build();
    }
}
