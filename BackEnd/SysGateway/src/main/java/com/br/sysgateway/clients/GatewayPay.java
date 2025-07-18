package com.br.sysgateway.clients;

import com.br.sysgateway.dto.PaymentDTO;
import com.br.sysgateway.dto.PaymentResponseDTO;
import com.br.sysgateway.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gatewayPay", url = "${payment.gateway.service.url}")
public interface GatewayPay {
    @PostMapping("/payment")
    ResponseDTO sendPayment(@RequestBody PaymentDTO dto);
}
