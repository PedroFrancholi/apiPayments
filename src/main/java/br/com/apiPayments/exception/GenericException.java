package br.com.apiPayments.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {

    private HttpStatus httpStatus;

    public GenericException(String mensagem, HttpStatus httpStatus) {
        super(mensagem);
        this.httpStatus = httpStatus;
    }

    public GenericException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
