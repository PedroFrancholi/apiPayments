package br.com.apiPayments.web.controller;

import br.com.apiPayments.facade.ConsultFacade;
import br.com.apiPayments.web.dto.response.ComponentResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consult")
@Tag(name = "Consult", description = "Endpoints for consult data.")
public class ConsultController {

    @Autowired
    private ConsultFacade consultFacade;

    @GetMapping("/component/{cdDocument}")
    private ResponseEntity<ComponentResponseDto> consultComponent(@PathVariable String cdDocument){
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultComponentByCdDocument(cdDocument));
    }
}
