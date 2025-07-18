package com.gateway.paymentgateway.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private PaymentResponseDTO paymentResponse;
    private String statusCode;
    private String internalErrorMessage;
}

