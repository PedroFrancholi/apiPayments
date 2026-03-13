package br.com.apiPayments.facade;

import br.com.apiPayments.model.HistoricalModel;
import br.com.apiPayments.repository.HistoricalRepository;
import br.com.apiPayments.web.dto.request.HistoricalRequestDto;
import br.com.apiPayments.web.dto.response.HistoricalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterFacade {

    @Autowired
    private HistoricalRepository historicalRepository;

    public HistoricalResponseDto createHistorical(HistoricalRequestDto bodyHistorical){
        try{

            HistoricalModel historical = HistoricalModel.builder()
                    .dsHistorical(bodyHistorical.getDsHistorical())
                    .dsReversalHistorical(bodyHistorical.getDsReversalHistorical())
                    .dtCreatedAt(LocalDateTime.now())
                    .build();

            historicalRepository.save(historical);

            return HistoricalResponseDto.builder()
                    .idHistorical(historical.getIdHistorical())
                    .dsHistorical(historical.getDsHistorical())
                    .dsReversalHistorical(historical.getDsReversalHistorical())
                    .dtCreatedAt(historical.getDtCreatedAt())
                    .build();

        }catch (Exception e){
            throw new RuntimeException("Error creating historical");
        }
    }
}
