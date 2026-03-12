package br.com.apiPayments.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/historical")
@Tag(name = "Historical", description = "Endpoints to register and maintenance of historical")
public class HistoricalController {
}
