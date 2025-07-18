package com.gateway.paymentgateway.config;
import com.mercadopago.client.payment.PaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayMPConfig {
    @Value("${mercadopago.accessToken}")
    private String accessToken;

    @Bean
    public PaymentClient getPaymentClient(){
        com.mercadopago.MercadoPagoConfig.setAccessToken(accessToken);
        return new PaymentClient();
    }
}
