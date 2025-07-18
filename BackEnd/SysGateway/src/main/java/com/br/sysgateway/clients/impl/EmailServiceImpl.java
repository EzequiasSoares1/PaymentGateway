package com.br.sysgateway.clients.impl;

import com.br.sysgateway.clients.EmailService;
import com.br.sysgateway.dto.EmailMessageDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;

import static java.util.Objects.nonNull;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final EmailService emailService;

    public void sendEmail(EmailMessageDTO emailMessage) {
        try {
            ResponseEntity<Void> response = emailService.sendEmail(emailMessage.getTo(),
                    emailMessage.getSubject(),
                    emailMessage.getContent(),
                    nonNull(emailMessage.getAttachment())?emailMessage.getAttachment():null
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Erro ao enviar e-mail: Status {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Exceção ao enviar e-mail", e);
        }
    }
}
