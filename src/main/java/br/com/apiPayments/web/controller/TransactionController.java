package br.com.apiPayments.web.controller;


import br.com.apiPayments.facade.TransactionFacade;
import br.com.apiPayments.web.dto.request.TransactionRequestDto;
import br.com.apiPayments.web.dto.response.TransactionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Endpoints for recording and maintaining of transactions")
public class TransactionController {

    @Autowired
    private TransactionFacade transactionFacade;

    @PostMapping
    @Operation(summary = "Register new transaction", description = "Create new banking transaction")
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto transactionBody){
        return ResponseEntity.status(HttpStatus.OK).body(transactionFacade.createTransaction(transactionBody));
    }
}
