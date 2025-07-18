package com.emailnotify.rabbitmq.consumers;
import com.emailnotify.dto.EmailMessageDTO;
import com.emailnotify.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class EmailConsumer {
    private EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listenEmail(@Payload EmailMessageDTO emailMessage) {
        log.info("Processing Receipt email for queue to: {}", emailMessage.getTo());
        emailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getContent(), emailMessage.getAttachment());
    }
}
