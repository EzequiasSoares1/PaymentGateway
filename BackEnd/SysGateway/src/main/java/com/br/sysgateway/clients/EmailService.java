package com.br.sysgateway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "emailClient", url = "${email.service.url}")
public interface EmailService {

    @PostMapping
    ResponseEntity<Void> sendEmail(@RequestPart("to") String to,
                                   @RequestPart("subject") String subject,
                                   @RequestPart("content") String content,
                                   @RequestPart(value = "file", required = false) MultipartFile file);
}
