package br.com.apiPayments.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionAccountResponseDto {
    private AccountResponseDto account;
    private List<TransactionResponseDto> trasaction;
}
