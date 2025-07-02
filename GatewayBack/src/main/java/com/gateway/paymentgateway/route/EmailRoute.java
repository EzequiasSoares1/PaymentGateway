package com.gateway.paymentgateway.route;

import com.gateway.paymentgateway.processor.EmailProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@Configuration
@RequiredArgsConstructor
public class EmailRoute extends RouteBuilder {

    private final EmailProcessor emailProcessor;

    @Override
    public void configure() throws Exception {
        from("direct:sendEmail")
                .process(emailProcessor)
                .marshal().json(JsonLibrary.Jackson);

    }
}
