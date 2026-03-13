package br.com.apiPayments.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consult")
@Tag(name = "Consult", description = "Endpoints for consult data.")
public class ConsultController {
}
