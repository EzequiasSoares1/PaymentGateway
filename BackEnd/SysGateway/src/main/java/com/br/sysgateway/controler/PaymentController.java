package com.br.sysgateway.controler;
import com.br.sysgateway.dto.PaymentDTO;
import com.br.sysgateway.dto.TransactionResponseDTO;
import com.br.sysgateway.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createPayment(@RequestBody PaymentDTO payment) {
        TransactionResponseDTO created = transactionService.createPayment(payment);
        return ResponseEntity.ok(created);
    }

}
