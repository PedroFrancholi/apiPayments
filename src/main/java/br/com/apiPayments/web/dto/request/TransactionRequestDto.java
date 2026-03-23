package br.com.apiPayments.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionRequestDto {

    @NotEmpty
    private Integer nrOriginAccount;

    private Integer nrDestinationAccount;

    @NotEmpty
    private BigDecimal vlTransaction;

    @NotEmpty
    private Integer cdHistorical;

    private String dsDetail;
}
