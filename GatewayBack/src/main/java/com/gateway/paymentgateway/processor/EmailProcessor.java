package com.gateway.paymentgateway.processor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.paymentgateway.dto.EmailMessageDTO;
import com.gateway.paymentgateway.exception.InternalErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailProcessor implements Processor {

    @Value("${base.url.email}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void process(Exchange exchange) {
        EmailMessageDTO email = exchange.getMessage().getBody(EmailMessageDTO.class);
        sendEmail(email);
    }

    private void sendEmail(EmailMessageDTO email) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EmailMessageDTO> request = new HttpEntity<>(email, headers);

            restTemplate.postForEntity(baseUrl, request, Void.class);

            log.info("Email enviado com sucesso para {}", email.getTo());
        } catch (Exception e) {
            log.error("Erro ao enviar email para {}: {}", email.getTo(), e.getMessage(), e);
            throw new InternalErrorException("Falha ao enviar email: " + e.getMessage());
        }
    }
}
