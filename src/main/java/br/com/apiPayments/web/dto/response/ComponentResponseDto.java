package br.com.apiPayments.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentResponseDto {

    private UUID cdComponent;
    private String nmPerson;
    private String cdDocument;
    private String tpPerson;
    private String dsEmail;
    private String dsPassword;
    private LocalDateTime dtCreatedAt;
}
