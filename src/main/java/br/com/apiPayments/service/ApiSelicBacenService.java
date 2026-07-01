package br.com.apiPayments.service;

import br.com.apiPayments.web.dto.response.SelicResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
@FeignClient(name = "Selic", url = "${client.url.selic}/dados/serie/bcdata.sgs.11")
public interface ApiSelicBacenService {

    @GetMapping("/dados")
    public List<SelicResponseDto> getSelic(
            @RequestParam String  formato,
            @RequestParam String dataInicial,
            @RequestParam String dataFinal
    );
}
