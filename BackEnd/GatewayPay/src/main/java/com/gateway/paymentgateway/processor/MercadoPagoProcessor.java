package com.gateway.paymentgateway.processor;
import com.gateway.paymentgateway.dto.PaymentDTO;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.*;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MercadoPagoProcessor {

    @Value("${base.url.notification.payment}")
    private String baseUrl;
    private final PaymentClient paymentClient;

    public Payment cardPayment(PaymentDTO cardPaymentDTO) throws MPException, MPApiException {
        PaymentAdditionalInfoRequest info = PaymentAdditionalInfoRequest.builder()
                .payer(PaymentAdditionalInfoPayerRequest.builder()
                        .firstName(cardPaymentDTO.getPayerDTO().getFirstName()).build())
                .items(List.of(
                        PaymentItemRequest.builder()
                                .id(cardPaymentDTO.getProductDTO().getId())
                                .title(cardPaymentDTO.getProductDTO().getName())
                                .quantity(1)
                                .unitPrice(cardPaymentDTO.getProductDTO().getValue())
                                .description(cardPaymentDTO.getProductDTO().getDescription())
                                .build()
                ))
                .build();

        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .additionalInfo(info)
                .transactionAmount(cardPaymentDTO.getCardPaymentDTO().getTransactionAmount())
                .token(cardPaymentDTO.getCardPaymentDTO().getToken())
                .description(cardPaymentDTO.getProductDTO().getDescription())
                .installments(cardPaymentDTO.getCardPaymentDTO().getInstallments())
                .paymentMethodId(cardPaymentDTO.getCardPaymentDTO().getPaymentMethodId())
                .notificationUrl(baseUrl)
                .payer(PaymentPayerRequest.builder()
                        .email(cardPaymentDTO.getPayerDTO().getEmail())
                        .identification(IdentificationRequest.builder()
                                .type(cardPaymentDTO.getPayerDTO().getType())
                                .number(cardPaymentDTO.getPayerDTO().getNumber()).build())
                        .build())
                .externalReference(cardPaymentDTO.getProductDTO().getId())
                .statementDescriptor(cardPaymentDTO.getProductDTO().getName())
                .build();

        return paymentClient.create(request);
    }

    public Payment pixPayment(PaymentDTO pixDTO) throws MPException, MPApiException {
        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .transactionAmount(pixDTO.getProductDTO().getValue())
                .description(pixDTO.getProductDTO().getDescription())
                .paymentMethodId("pix")
                .payer(PaymentPayerRequest.builder()
                        .firstName(pixDTO.getPayerDTO().getFirstName())
                        .email(pixDTO.getPayerDTO().getEmail())
                        .build())
                .build();

        return paymentClient.create(request);
    }

    public Preference createPreference(PaymentDTO request) throws MPException, MPApiException {
            PreferenceClient client = new PreferenceClient();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(PreferenceItemRequest.builder()
                    .id(request.getProductDTO().getId())
                    .title(request.getProductDTO().getName())
                    .quantity(request.getProductDTO().getMount())
                    .unitPrice(request.getProductDTO().getValue())
                    .currencyId("BRL")
                    .build());

            PreferenceRequest preferenceRequest = PreferenceRequest.builder().items(items).build();
            return client.create(preferenceRequest);
    }
}
