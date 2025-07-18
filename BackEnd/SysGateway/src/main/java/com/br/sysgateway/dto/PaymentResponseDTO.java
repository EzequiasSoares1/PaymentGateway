package com.br.sysgateway.dto;
import com.br.sysgateway.enums.PaymentType;
import com.br.sysgateway.enums.ProviderType;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponseDTO {
    private Long externalId;
    private PaymentType paymentType;
    private String status;
    private BigDecimal amount;
    private String currency;
    private String description;
    private LocalDateTime transactionDate;
    private ProviderType paymentProvider;
    private String base64Image;
    private String preferenceId;
}
