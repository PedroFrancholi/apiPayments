package br.com.apiPayments.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SizeDocumentComponentEnum {

    NATURAL_PERSON(11),
    LEGAL_PERSON(14);

    private final Integer value;
}
