package com.emailnotify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDTO {
    @NotBlank
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
    private MultipartFile attachment;
}
