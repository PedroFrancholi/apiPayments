package br.com.apiPayments.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponseDto {

    private UUID cdAccount;
    private Integer nrAccount;
    private Integer cdAgency;
    private BigDecimal vlAmount;
    private LocalDateTime dtCreatedAt;
    private ComponentResponseDto component;
}
