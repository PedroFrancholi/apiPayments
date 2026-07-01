package br.com.apiPayments.web.controller;

import br.com.apiPayments.facade.SelicFacade;
import br.com.apiPayments.web.dto.response.SelicResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/selic")
@Tag(name = "Selic", description = "Endpoint for consult the daily Selic value.")
public class SelicController {

    @Autowired
    private SelicFacade selicFacade;

    @GetMapping
    public ResponseEntity<List<SelicResponseDto>> getSelic(
            @RequestParam String format,
            @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate){
        return ResponseEntity.status(HttpStatus.OK).body(selicFacade.getSelic(format, startDate, endDate));
    }
}
