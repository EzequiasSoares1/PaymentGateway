package com.br.sysgateway.service;

import com.br.sysgateway.clients.impl.EmailServiceImpl;
import com.br.sysgateway.clients.impl.GatewayPayServiceImpl;
import com.br.sysgateway.converter.PaymentConverter;
import com.br.sysgateway.dto.*;
import com.br.sysgateway.exceptions.PaymentFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final GatewayPayServiceImpl gatewayPayService;
    private final PaymentService paymentService;
    private final EmailServiceImpl notificationEmailService;

    public TransactionResponseDTO createPayment(PaymentDTO operationDTO){
        ResponseDTO processedPayment = gatewayPayService.processPayment(operationDTO);

        if(processedPayment.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR.toString())) {
            log.error("Error api gateway: {}", processedPayment.getInternalErrorMessage());
            throw new PaymentFailedException("Erro ao processar pagamento via gateway, tente mais tarde");
        }

        saveTransactionHistory(processedPayment.getPaymentResponse());
        sendEmail(processedPayment.getPaymentResponse(),operationDTO);
        return PaymentConverter.toTransactionResponseDTO(processedPayment.getPaymentResponse());
    }

    @Async
    public void sendEmail(PaymentResponseDTO responseDTO, PaymentDTO paymentDTO){
        notificationEmailService.sendEmail(
                EmailMessageDTO.builder()
                        .to(paymentDTO.getPayerDTO().getEmail())
                        .subject("Pagamento via Gateway")
                        .content("Status: "+ responseDTO.getStatus() +
                                "\n Valor: "+ responseDTO.getAmount() +
                                "\n Transacao: "+ responseDTO.getTransactionDate() +
                                "\n Descrição: "+ responseDTO.getDescription())
                        .build()
        );
    }

    private void saveTransactionHistory(PaymentResponseDTO dto) {
        paymentService.save(
                PaymentConverter.toEntity(dto)
        );
    }

}