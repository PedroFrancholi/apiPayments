package br.com.apiPayments.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricalRequestDto {

    @NotEmpty
    private Integer idHistorical;
    @NotEmpty
    private String dsHistorical;
    @NotEmpty
    private Boolean inAccount;
    @NotEmpty
    private String dsReversalHistorical;
}
