package br.com.apiPayments.web.controller;

import br.com.apiPayments.facade.ConsultFacade;
import br.com.apiPayments.web.dto.response.ComponentResponseDto;
import br.com.apiPayments.web.dto.response.HistoricalResponseDto;
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
    private ResponseEntity<List<ComponentResponseDto>> consultComponents() {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultComponents());
    }

    @GetMapping("/component/{cdDocument}")
    private ResponseEntity<ComponentResponseDto> consultComponentById(@PathVariable String cdDocument) {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultComponentByCdDocument(cdDocument));
    }

    @GetMapping("/historical")
    private ResponseEntity<List<HistoricalResponseDto>> consultHistoricals() {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultHistoricals());
    }

    @GetMapping("/historical/{idHistorical}")
    private ResponseEntity<HistoricalResponseDto> consultHistoricalById(@PathVariable Integer idHistorical) {
        return ResponseEntity.status(HttpStatus.OK).body(consultFacade.consultHistoricalById(idHistorical));
    }
}