package br.com.apiPayments.web.controller;

import br.com.apiPayments.facade.ConsultFacade;
import br.com.apiPayments.web.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consult")
@Tag(name = "Consult", description = "Endpoints for consult data.")
public class ConsultController {

    @Autowired
    private ConsultFacade consultFacade;

    @GetMapping("/component")
    @Operation(summary = "Consult all components.", description = "Consult all components that registered.")
    private ResponseEntity<List<ComponentResponseDto>> consultComponents() {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultComponents());
    }

    @GetMapping("/component/{cdDocument}")
    @Operation(summary = "Consult a component.", description = "Consult component by document code.")
    private ResponseEntity<ComponentResponseDto> consultComponentById(@PathVariable String cdDocument) {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultComponentByCdDocument(cdDocument));
    }

    @GetMapping("/historical")
    @Operation(summary = "Consult all historical.", description = "Consult all historical that registered.")
    private ResponseEntity<List<HistoricalResponseDto>> consultHistoricals() {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultHistoricals());
    }

    @GetMapping("/historical/{idHistorical}")
    @Operation(summary = "Consult an historical.", description = "Consult historical by id historical.")
    private ResponseEntity<HistoricalResponseDto> consultHistoricalById(@PathVariable Integer idHistorical) {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultHistoricalById(idHistorical));
    }

    @GetMapping("/account/{nrAccount}")
    @Operation(summary = "Consult an account.", description = "Consult account by account number.")
    private ResponseEntity<AccountResponseDto> consultAccountById(@PathVariable Integer nrAccount) {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultAccountByNrAccount(nrAccount));
    }

    @GetMapping("/account")
    @Operation(summary = "Consult all accounts.", description = "Consult all accounts that registered.")
    private ResponseEntity<List<AccountResponseDto>> consultAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultAccounts());
    }

    @GetMapping("/transaction")
    @Operation(summary = "Consult all transaction.", description = "Consult all transaction that registered.")
    private ResponseEntity<List<TransactionResponseDto>> consultTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultTransactions());
    }

    @GetMapping("/transaction/originAccount")
    @Operation(summary = "Consult all transaction of an origin account.", description = "Consult all transaction that registered by number origin account.")
    private ResponseEntity<TransactionAccountResponseDto> consultTransactionByNrOriginAccount(@RequestParam Integer nrOriginAccount) {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultTransactionByNrOriginAccount(nrOriginAccount));
    }

    @GetMapping("/transaction/account")
    @Operation(summary = "Consult all transaction of an account.", description = "Consult all transaction that registered by number account.")
    private ResponseEntity<TransactionAccountResponseDto> consultTransactionByNrAccount(@RequestParam Integer nrAccount) {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultTransactionByNrAccount(nrAccount));
    }

    @GetMapping("/transaction/pending")
    @Operation(summary = "Consult all transaction of an account.", description = "Consult all transaction that registered of .")
    private ResponseEntity<List<TransactionResponseDto>> consultTransactionPending() {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultTransactionPending());
    }
}