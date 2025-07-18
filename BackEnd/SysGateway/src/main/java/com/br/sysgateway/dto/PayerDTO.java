package com.br.sysgateway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayerDTO {
    @NotNull
    private String email;

    @NotNull
    private String type;

    @NotNull
    private String number;

    @NotNull
    private String firstName;
}
