package com.gateway.paymentgateway.route;
import com.gateway.paymentgateway.dto.PaymentDTO;
import com.gateway.paymentgateway.processor.MercadoPagoProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MercadoPagoRoute extends RouteBuilder {

    private final MercadoPagoProcessor mercadoPagoProcessor;

    @Override
    public void configure() {

        rest("/payment")
                .post()
                .type(PaymentDTO.class)
                .produces("application/json")
                .to("direct:gatewayPagamento");

        from("direct:mercadopago")
                .process(mercadoPagoProcessor)
                .setHeader("Content-Type", constant("application/json"));
    }
}
