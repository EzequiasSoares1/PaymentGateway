package com.br.sysgateway.dto;

import com.br.sysgateway.enums.PaymentType;
import com.br.sysgateway.enums.ProviderType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class PaymentDTO {
    private CardPaymentDTO cardPaymentDTO;
    private PayerDTO payerDTO;
    private ProductDTO productDTO;
    private ProviderType providerType;
    private PaymentType paymentType;
    private String token;
    private Long idStatus;
}