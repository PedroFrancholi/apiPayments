package br.com.apiPayments.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentRequestDto {

    @NotEmpty
    private String nmPerson;
    @NotEmpty
    private String cdDocument;
    @NotEmpty
    private String tpPerson;
    @NotEmpty
    private String dsEmail;
    @NotEmpty
    private String dsPassword;
}
