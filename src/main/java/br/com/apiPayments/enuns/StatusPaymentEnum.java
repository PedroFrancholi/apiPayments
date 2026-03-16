package br.com.apiPayments.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPaymentEnum {

    PENDING("P"),
    EFFECTIVATED("E"),
    ERROR("R");

    private final String value;

    public static String fromValue(String value){
        for (StatusPaymentEnum status : values()) {
            if(status.value.equals(value)){
                return status.value;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
