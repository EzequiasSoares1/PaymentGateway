package com.emailnotify.controller;

import com.emailnotify.dto.EmailMessageDTO;
import com.emailnotify.service.EmailService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class EmailSendController {

    private EmailService emailService;

    @PostMapping
    public void sendEmail(@RequestBody EmailMessageDTO emailMessageDTO) {
        emailService.sendEmail(emailMessageDTO.getTo(),emailMessageDTO.getSubject(),emailMessageDTO.getContent(),emailMessageDTO.getAttachment());
    }

}
