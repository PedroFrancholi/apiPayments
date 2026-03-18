package br.com.apiPayments.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricalResponseDto {
    private Integer idHistorical;
    private String dsHistorical;
    private String dsReversalHistorical;
    private Boolean inAccount;
    private LocalDateTime dtCreatedAt;
}
