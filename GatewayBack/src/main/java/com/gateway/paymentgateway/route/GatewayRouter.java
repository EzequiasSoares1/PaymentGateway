package com.gateway.paymentgateway.route;
import org.apache.camel.Configuration;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class GatewayRouter extends RouteBuilder {
    @Override
    public void configure() {
        restConfiguration()
                .component("platform-http")
                .contextPath("/api/v1")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Gateway Payment API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .bindingMode(RestBindingMode.json);

        from("direct:gatewayPagamento")
            .choice()
                .when(simple("${body.providerType} == 'MERCADOPAGO'"))
                .to("direct:mercadopago")
                .when(simple("${body.providerType} == 'PAGSEGURO'"))
                .to("direct:pagseguro")
                .otherwise()
                .to("direct:default");


    }
}
