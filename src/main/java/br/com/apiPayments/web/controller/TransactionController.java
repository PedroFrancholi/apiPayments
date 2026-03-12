package br.com.apiPayments.web.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Endpoints to register and maintenance of transactions")
public class TransactionController {

    @GetMapping
    public String test(){
        return "Hello World";
    }
}
