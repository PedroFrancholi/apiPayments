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
public class TransactionResponseDto {

    private UUID cdTransaction;
    private AccountResponseDto originAccount;
    private AccountResponseDto destinationAccount;
    private BigDecimal vlTransaction;
    private HistoricalResponseDto historical;
    private String cdStatus;
    private String dsDetail;
    private LocalDateTime dtCreatedAt;
    private LocalDateTime dtProcessedAt;
}
