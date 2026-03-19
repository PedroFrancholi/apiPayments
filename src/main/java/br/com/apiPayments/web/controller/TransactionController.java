package br.com.apiPayments.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Endpoints for recording and maintaining of transactions")
public class TransactionController {

    @GetMapping
    @Operation(summary = "Register new transaction", description = "Create new banking transaction")
    public String test(){
        return "Hello World";
    }
}
