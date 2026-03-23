package br.com.apiPayments.facade;

import br.com.apiPayments.enuns.SizeDocumentComponentEnum;
import br.com.apiPayments.enuns.TypePersonEnum;
import br.com.apiPayments.exception.GenericException;
import br.com.apiPayments.model.AccountModel;
import br.com.apiPayments.model.ComponentModel;
import br.com.apiPayments.model.HistoricalModel;
import br.com.apiPayments.repository.AccountRepository;
import br.com.apiPayments.repository.ComponentRepository;
import br.com.apiPayments.repository.HistoricalRepository;
import br.com.apiPayments.util.LogUtil;
import br.com.apiPayments.web.dto.request.AccountRequestDto;
import br.com.apiPayments.web.dto.request.ComponentRequestDto;
import br.com.apiPayments.web.dto.request.HistoricalRequestDto;
import br.com.apiPayments.web.dto.response.AccountResponseDto;
import br.com.apiPayments.web.dto.response.ComponentResponseDto;
import br.com.apiPayments.web.dto.response.HistoricalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegisterFacade {

    @Autowired
    private HistoricalRepository historicalRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private AccountRepository accountRepository;

    public HistoricalResponseDto createHistorical(HistoricalRequestDto bodyHistorical){
        try{

            HistoricalModel historical = HistoricalModel.builder()
                    .dsHistorical(bodyHistorical.getDsHistorical())
                    .dsReversalHistorical(bodyHistorical.getDsReversalHistorical())
                    .inAccount(bodyHistorical.getInAccount())
                    .dtCreatedAt(LocalDateTime.now())
                    .build();

            historicalRepository.save(historical);

            return HistoricalResponseDto.builder()
                    .idHistorical(historical.getIdHistorical())
                    .dsHistorical(historical.getDsHistorical())
                    .dsReversalHistorical(historical.getDsReversalHistorical())
                    .inAccount(historical.getInAccount())
                    .dtCreatedAt(historical.getDtCreatedAt())
                    .build();

        }catch (GenericException e){
            LogUtil.error(this.getClass(), "Client error: " + e.getMessage(), e);
            throw e;
        } catch (IllegalArgumentException e) {
            LogUtil.error(this.getClass(), "Invalid argument: " + e.getMessage(), e);
            throw new GenericException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            LogUtil.error(this.getClass(), "Error during create historical.",e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ComponentResponseDto createComponent(ComponentRequestDto bodyComponent) {
        try {

            String documentFormated = bodyComponent.getCdDocument().replace(".","").replace("-","");

            if(documentFormated.length() != SizeDocumentComponentEnum.LEGAL_PERSON.getValue() & documentFormated.length() != SizeDocumentComponentEnum.NATURAL_PERSON.getValue()){
                throw new GenericException("Document code provided "+documentFormated+" invalid", HttpStatus.BAD_REQUEST);
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String passwordCripted = encoder.encode(bodyComponent.getDsPassword());

            ComponentModel componentModel = ComponentModel.builder()
                    .nmPerson(bodyComponent.getNmPerson())
                    .cdDocument(documentFormated)
                    .tpPerson(TypePersonEnum.validTypePerson(bodyComponent.getTpPerson()))
                    .dsEmail(bodyComponent.getDsEmail())
                    .dsPassword(passwordCripted)
                    .dtCreatedAt(LocalDateTime.now())
                    .build();

            componentRepository.save(componentModel);

            return ComponentResponseDto.builder()
                    .nmPerson(componentModel.getNmPerson())
                    .cdDocument(componentModel.getCdDocument())
                    .tpPerson(componentModel.getTpPerson())
                    .dsEmail(componentModel.getDsEmail())
                    .dtCreatedAt(componentModel.getDtCreatedAt())
                    .build();
        }catch (GenericException e){
            LogUtil.error(this.getClass(), "Client error: " + e.getMessage(), e);
            throw e;
        } catch (IllegalArgumentException e) {
            LogUtil.error(this.getClass(), "Invalid argument: " + e.getMessage(), e);
            throw new GenericException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            LogUtil.error(this.getClass(), "Error during create component.",e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AccountResponseDto createAccount(AccountRequestDto bodyAccount){
        try{

            ComponentModel componentByCdDocument = componentRepository.findByCdDocument(bodyAccount.getCdDocumentComponent())
                    .orElseThrow(() -> new GenericException("Component with document code "+bodyAccount.getCdDocumentComponent()+" not found!", HttpStatus.NOT_FOUND));

            AccountModel accountModel = AccountModel.builder()
                    .nrAccount(bodyAccount.getNrAccount())
                    .cdAgency(bodyAccount.getCdAgency())
                    .vlAmount(BigDecimal.ZERO)
                    .dtCreatedAt(LocalDateTime.now())
                    .dtLastUpdate(LocalDateTime.now())
                    .component(componentByCdDocument)
                    .build();

            accountRepository.save(accountModel);

            return AccountResponseDto.builder()
                    .nrAccount(accountModel.getNrAccount())
                    .cdAgency(accountModel.getCdAgency())
                    .vlAmount(accountModel.getVlAmount())
                    .dtCreatedAt(accountModel.getDtCreatedAt())
                    .component(ComponentResponseDto.builder()
                            .cdDocument(accountModel.getComponent().getCdDocument())
                            .nmPerson(accountModel.getComponent().getNmPerson())
                            .dsEmail(accountModel.getComponent().getDsEmail())
                            .build())
                    .build();

        }catch (GenericException e){
            LogUtil.error(this.getClass(), "Client error: " + e.getMessage(), e);
            throw e;
        } catch (IllegalArgumentException e) {
            LogUtil.error(this.getClass(), "Invalid argument: " + e.getMessage(), e);
            throw new GenericException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            LogUtil.error(this.getClass(), "Error during create component.",e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
