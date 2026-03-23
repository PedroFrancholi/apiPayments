package br.com.apiPayments.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypePersonEnum {

    NATURAL_PERSON("F"),
    LEGAL_PERSON("J");

    private final String value;

    public static String validTypePerson(String value){
        for (TypePersonEnum typePerson : values()) {
            if(typePerson.value.equals(value)){
                return typePerson.value;
            }
        }
        throw new IllegalArgumentException("Invalid type person: " + value);
    }
}

