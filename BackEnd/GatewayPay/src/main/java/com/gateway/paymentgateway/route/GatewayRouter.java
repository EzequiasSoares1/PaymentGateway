package com.gateway.paymentgateway.route;
import com.gateway.paymentgateway.dto.PaymentDTO;
import com.gateway.paymentgateway.mapper.PaymentResponseMapper;
import com.gateway.paymentgateway.processor.MercadoPagoProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Configuration;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.net.ContentHandler;

@Component
@Configuration
@RequiredArgsConstructor
public class GatewayRouter extends RouteBuilder {

    private final MercadoPagoProcessor mercadoPagoProcessor;

    @Override
    public void configure() {
        restConfiguration()
                .component("platform-http")
                .contextPath("/api")
                .bindingMode(RestBindingMode.json);

        from("direct:gatewayPagamento")
            .setProperty("paymentDTO", simple("${body}"))
            .choice()
                .when(simple("${body.providerType} == 'MERCADOPAGO'"))
                    .to("direct:mercadopago")
                .when(simple("${body.providerType} == 'STRIPE'"))
                    .to("direct:stripe")
                .otherwise()
                    .throwException(new IllegalArgumentException("Unsupported provider type: ${body.providerType}"));


        from("direct:mercadopago")
            .onException(Exception.class)
                .handled(true)
                .bean(PaymentResponseMapper.class, "error")
            .end()
            .choice()
                .when(simple("${body.paymentType} == 'CARD'"))
                    .bean(MercadoPagoProcessor.class, "cardPayment")
                .when(simple("${body.paymentType} == 'PIX'"))
                    .bean(MercadoPagoProcessor.class, "pixPayment")
                .when(simple("${body.paymentType} == 'CREATE_PREFERENCE'"))
                    .bean(MercadoPagoProcessor.class, "createPreference")
                .otherwise()
                    .throwException(new IllegalArgumentException("Unsupported payment type: ${body.paymentType}"))
            .end()
            .to("direct:responsePayment");

        from("direct:responsePayment")
            .choice()
                .when(simple("${exchangeProperty.paymentDTO.providerType} == 'MERCADOPAGO'"))
                    .bean(PaymentResponseMapper.class, "fromMercadoPago")
//                .when(simple("${body.paymentProvider} == 'PAGSEGURO'"))
//                    .bean(PaymentResponseMapper.class, "fromPagSeguro")
                .otherwise()
                    .throwException(new IllegalArgumentException("Unsupported payment provider: ${exchangeProperty.paymentDTO.providerType}"))
            .end()
            .log("Response body: ${body}");
    }
}
