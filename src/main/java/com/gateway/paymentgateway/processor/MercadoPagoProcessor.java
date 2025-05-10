package com.gateway.paymentgateway.processor;
import com.gateway.paymentgateway.dto.PaymentDTO;
import com.gateway.paymentgateway.service.mercadopago.MercadoPagoProvider;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.*;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.gateway.paymentgateway.exception.PaymentFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class MercadoPagoProcessor implements Processor {
    @Value("${base.url.notification.payment}")
    private String baseUrl;
    private final MercadoPagoProvider mercadoPagoProvider;
    private PaymentClient paymentClient;

    @Override
    public void process(Exchange exchange){
        PaymentDTO body = (PaymentDTO) exchange.getIn().getBody();
        paymentClient = mercadoPagoProvider.getPaymentClient(body.getToken());

        if (nonNull(body.getIdStatus())){
            exchange.getMessage().setBody(getStatusPaymentMl(body.getIdStatus()));
            return;
        }

        switch (body.getPaymentType().toString()) {
            case "CARD_PAYMENT" -> {
                Payment payment = cardPayment(body);
                exchange.getMessage().setBody(payment);
            }
            case "PIX" -> {
                Payment payment = pixPayment(body);
                exchange.getMessage().setBody(payment);
            }
            case "CREATE_PREFERENCE" -> {
                String id = createPreference(body);
                exchange.getMessage().setBody(id);
            }
            default -> throw new PaymentFailedException("Type payment not found");
        }
    }

    public Payment cardPayment(PaymentDTO cardPaymentDTO) {
        try {
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

        } catch (MPApiException | MPException ex) {
            throw new PaymentFailedException("Mercado Pago API: " + ex.getMessage());
        }
    }

    public Payment pixPayment(PaymentDTO pixDTO) {
        try {
            PaymentCreateRequest request = PaymentCreateRequest.builder()
                    .transactionAmount(pixDTO.getProductDTO().getValue())
                    .description(pixDTO.getProductDTO().getDescription())
                    .paymentMethodId("pix")
                    .notificationUrl(baseUrl)
                    .payer(PaymentPayerRequest.builder()
                            .firstName(pixDTO.getPayerDTO().getFirstName())
                            .email(pixDTO.getPayerDTO().getEmail())
                            .build())
                    .build();

            return paymentClient.create(request);

        } catch (MPApiException | MPException ex) {
            throw new PaymentFailedException("Mercado Pago API: " + ex.getMessage());
        }
    }

    public String getStatusPaymentMl(long id) {
        try {
            return paymentClient.get(id).getStatus();
        } catch (MPApiException | MPException ex) {
            throw new PaymentFailedException("Mercado Pago API: " + ex.getMessage());
        }
    }

    public String createPreference(PaymentDTO request) {
        try {
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
            Preference preference = client.create(preferenceRequest);

            return preference.getId();

        } catch (MPException | MPApiException ex) {
            throw new PaymentFailedException("Mercado Pago API: " + ex.getMessage());
        }
    }
}
