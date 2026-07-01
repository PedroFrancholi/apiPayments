package br.com.apiPayments.facade;

import br.com.apiPayments.exception.GenericException;
import br.com.apiPayments.service.ApiSelicBacenService;
import br.com.apiPayments.util.LogUtil;
import br.com.apiPayments.web.dto.response.SelicResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SelicFacade {

    @Autowired
    private ApiSelicBacenService apiSelicBacenService;

    public List<SelicResponseDto> getSelic(String format, LocalDate startDate, LocalDate endDate) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return apiSelicBacenService.getSelic(format, startDate.format(formatter), endDate.format(formatter));

        }catch (GenericException e){
            LogUtil.error(this.getClass(), "Client error: " + e.getMessage(), e);
            throw e;
        }catch (Exception e){
            LogUtil.error(this.getClass(), "Error during create component.",e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
