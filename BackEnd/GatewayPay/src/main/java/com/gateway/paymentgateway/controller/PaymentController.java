package com.gateway.paymentgateway.controller;

import com.gateway.paymentgateway.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final ProducerTemplate producerTemplate;

    @PostMapping
    public ResponseEntity<?> sendPayment(@RequestBody PaymentDTO dto) {
        try {
            Object resposta = producerTemplate.requestBody("direct:gatewayPagamento", dto);
            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar pagamento: " + e.getMessage());
        }
    }
}
