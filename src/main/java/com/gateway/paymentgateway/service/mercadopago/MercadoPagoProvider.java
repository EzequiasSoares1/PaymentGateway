package com.gateway.paymentgateway.service.mercadopago;

import com.mercadopago.client.payment.PaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoProvider {
    @Value("${mercadopago.accessToken}")
    private String accessToken;

    public PaymentClient getPaymentClient(String token) {
        com.mercadopago.MercadoPagoConfig.setAccessToken(token);
        return new PaymentClient();
    }

    public PaymentClient getPaymentClient(){
        com.mercadopago.MercadoPagoConfig.setAccessToken(accessToken);
        return new PaymentClient();
    }
}
