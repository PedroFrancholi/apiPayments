package br.com.apiPayments.web.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricalResponseDto {

    @NotEmpty
    private Integer idHistorical;
    @NotEmpty
    private String dsHistorical;
    @NotEmpty
    private String dsReversalHistorical;
    @NotEmpty
    private LocalDateTime dtCreatedAt;
}
