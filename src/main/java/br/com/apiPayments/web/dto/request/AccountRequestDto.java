package br.com.apiPayments.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    @NotEmpty
    private Integer nrAccount;

    @NotEmpty
    private Integer cdAgency;

    @NotEmpty
    private String cdDocumentComponent;
}
