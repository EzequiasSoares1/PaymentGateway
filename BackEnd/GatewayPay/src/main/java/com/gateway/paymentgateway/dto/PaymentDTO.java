package com.gateway.paymentgateway.dto;
import com.gateway.paymentgateway.enums.PaymentType;
import com.gateway.paymentgateway.enums.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    private CardPaymentDTO cardPaymentDTO;
    private PayerDTO payerDTO;
    private ProductDTO productDTO;
    private ProviderType providerType;
    private PaymentType paymentType;
    private String token;
    private Long idStatus;
}