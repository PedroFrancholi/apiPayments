package br.com.apiPayments.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {

    private UUID cdAccount;
    private Integer nrAccount;
    private Integer cdAgency;
    private BigDecimal vlAmout;
    private LocalDateTime dtCreatedAt;
    private ComponentResponseDto component;
}
