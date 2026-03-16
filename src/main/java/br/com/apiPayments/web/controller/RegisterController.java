package br.com.apiPayments.web.controller;

import br.com.apiPayments.facade.RegisterFacade;
import br.com.apiPayments.web.dto.request.AccountRequestDto;
import br.com.apiPayments.web.dto.request.ComponentRequestDto;
import br.com.apiPayments.web.dto.request.HistoricalRequestDto;
import br.com.apiPayments.web.dto.response.AccountResponseDto;
import br.com.apiPayments.web.dto.response.ComponentResponseDto;
import br.com.apiPayments.web.dto.response.HistoricalResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@Tag(name = "Register", description = "Endpoints for recording and maintaining the data involved in the application.")
public class RegisterController {

    @Autowired
    private RegisterFacade registerFacade;

    @PostMapping("/historical")
    @Operation(summary = "Create new historical", description = "Create new historical of account moving")
    public ResponseEntity<HistoricalResponseDto> createHistorical(@RequestBody HistoricalRequestDto bodyHistorical){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerFacade.createHistorical(bodyHistorical));
    }

    @PostMapping("/component")
    @Operation(summary = "Create new component", description = "Create new component to link with account")
    public ResponseEntity<ComponentResponseDto> createComponent(@RequestBody ComponentRequestDto bodyComponent){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerFacade.createComponent(bodyComponent));
    }

    @PostMapping("/account")
    @Operation(summary = "Create new component", description = "Create new account")
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto bodyAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerFacade.createAccount(bodyAccount));
    }
}
