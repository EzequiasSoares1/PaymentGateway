package com.emailnotify.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String auth;

    public void sendEmail(String to, String subject, String content, MultipartFile attachment) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            log.debug("Preparing email to be sent to {}", to);

            helper.setFrom(auth);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            if(nonNull(attachment))
                helper.addAttachment(attachment.getOriginalFilename(),  () -> attachment.getInputStream(), attachment.getContentType());

            log.debug("Email prepared successfully, sending now...");

            mailSender.send(message);
            log.info("Email sent to {} success", to);

        } catch (MessagingException e) {
            log.error("Failed to send email to {}", to, e);
        }
    }


}
