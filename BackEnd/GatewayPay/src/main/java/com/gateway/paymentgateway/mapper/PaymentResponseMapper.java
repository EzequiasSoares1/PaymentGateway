package com.gateway.paymentgateway.mapper;
import com.gateway.paymentgateway.dto.PaymentDTO;
import com.gateway.paymentgateway.dto.PaymentResponseDTO;
import com.gateway.paymentgateway.dto.ResponseDTO;
import com.gateway.paymentgateway.enums.ProviderType;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentPointOfInteraction;
import com.mercadopago.resources.payment.PaymentTransactionData;
import com.mercadopago.resources.preference.Preference;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PaymentResponseMapper {

    public ResponseDTO fromMercadoPago(Exchange exchange) {
        Payment payment = exchange.getIn().getBody(Payment.class);
        Preference preference = exchange.getProperty("preference", Preference.class);

        PaymentResponseDTO paymentResponseDTO = PaymentResponseDTO.builder()
                .externalId(payment.getId())
                .amount(payment.getTransactionAmount())
                .status(safeUpper(payment.getStatus()))
                .currency(payment.getCurrencyId())
                .paymentProvider(ProviderType.MERCADOPAGO)
                .transactionDate(LocalDateTime.now())
                .description(payment.getDescription())
                .paymentType(safeUpper(payment.getPaymentMethodId()))
                .base64Image(
                        Optional.ofNullable(payment.getPointOfInteraction())
                                .map(PaymentPointOfInteraction::getTransactionData)
                                .map(PaymentTransactionData::getQrCodeBase64)
                                .orElse("")
                )
                .preferenceId(
                        Optional.ofNullable(preference)
                                .map(Preference::getId)
                                .orElse("")
                )
                .build();

        return ResponseDTO.builder()
                .paymentResponse(paymentResponseDTO)
                .statusCode(HttpStatus.CREATED.toString())
                .build();
    }

    public ResponseDTO error(Exchange exchange) {
        PaymentDTO payment = exchange.getProperty("paymentDTO", PaymentDTO.class);
        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String messageError;
        if(exception instanceof MPApiException mpException) {
            messageError = mpException.getApiResponse().getContent();
        } else {
            messageError = exception.getMessage();
        }

        PaymentResponseDTO paymentResponseDTO = PaymentResponseDTO.builder()
                .paymentProvider(payment.getProviderType())
                .paymentType(payment.getPaymentType().toString())
                .build();

        return ResponseDTO.builder()
                .paymentResponse(paymentResponseDTO)
                .internalErrorMessage(messageError)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .build();
    }

    private String safeUpper(String str) {
        return str != null ? str.toUpperCase() : "";
    }
}
