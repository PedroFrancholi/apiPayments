package br.com.apiPayments.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/account")
@Tag(name = "Account", description = "Endpoints to register and maintenance of accounts")
public class AccountController {
}
