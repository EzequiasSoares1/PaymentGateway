package com.br.sysgateway.clients.impl;

import com.br.sysgateway.clients.GatewayPay;
import com.br.sysgateway.dto.PaymentDTO;
import com.br.sysgateway.dto.PaymentResponseDTO;
import com.br.sysgateway.dto.ResponseDTO;
import com.br.sysgateway.dto.TransactionResponseDTO;
import com.br.sysgateway.exceptions.PaymentFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayPayServiceImpl {

    private final GatewayPay gatewayPay;

    public ResponseDTO processPayment(PaymentDTO dto) {
        try {
            log.info("Sending payment to gateway: {}", dto);
            ResponseDTO response = gatewayPay.sendPayment(dto);
            log.info("Payment processed successfully. Gateway response: {}", response);
            return response;
        } catch (Exception e) {
            throw new PaymentFailedException("Failed to process payment through gateway: " + e.getMessage());
        }
    }
}

