package br.com.apiPayments.facade;

import br.com.apiPayments.model.ComponentModel;
import br.com.apiPayments.repository.ComponentRepository;
import br.com.apiPayments.web.dto.response.ComponentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultFacade {

    @Autowired
    private ComponentRepository componentRepository;

    public ComponentResponseDto consultComponentByCdDocument(String cdDocument){

        Optional<ComponentModel> component = componentRepository.findByCdDocument(cdDocument);

        return ComponentResponseDto.builder()
                .cdComponent(component.get().getCdComponent())
                .nmPerson(component.get().getNmPerson())
                .build();

    }
}
