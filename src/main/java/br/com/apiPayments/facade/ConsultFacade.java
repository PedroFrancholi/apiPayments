package br.com.apiPayments.facade;

import br.com.apiPayments.exception.GenericException;
import br.com.apiPayments.model.AccountModel;
import br.com.apiPayments.model.ComponentModel;
import br.com.apiPayments.model.HistoricalModel;
import br.com.apiPayments.repository.AccountRepository;
import br.com.apiPayments.repository.ComponentRepository;
import br.com.apiPayments.repository.HistoricalRepository;
import br.com.apiPayments.util.LogUtil;
import br.com.apiPayments.web.dto.response.AccountResponseDto;
import br.com.apiPayments.web.dto.response.ComponentResponseDto;
import br.com.apiPayments.web.dto.response.HistoricalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultFacade {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private HistoricalRepository historicalRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<ComponentResponseDto> consultComponents() {
        try{
            List<ComponentModel> components = componentRepository.findAll();

            if(components.isEmpty()){
                throw new GenericException("No components found!", HttpStatus.NOT_FOUND);
            }

            return components.stream()
                    .map(component -> ComponentResponseDto.builder()
                            .nmPerson(component.getNmPerson())
                            .cdDocument(component.getCdDocument())
                            .tpPerson(component.getTpPerson())
                            .dsEmail(component.getDsEmail())
                            .dtCreatedAt(component.getDtCreatedAt())
                            .build())
                    .toList();
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

    public ComponentResponseDto consultComponentByCdDocument(String cdDocument){
        try{
            ComponentModel component = componentRepository.findByCdDocument(cdDocument)
                    .orElseThrow(() ->new GenericException("Component with document code "+cdDocument+" not found!", HttpStatus.NOT_FOUND));

            return ComponentResponseDto.builder()
                    .nmPerson(component.getNmPerson())
                    .cdDocument(component.getCdDocument())
                    .tpPerson(component.getTpPerson())
                    .dsEmail(component.getDsEmail())
                    .dtCreatedAt(component.getDtCreatedAt())
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

    public List<HistoricalResponseDto> consultHistoricals() {
        try{
            List<HistoricalModel> historicals = historicalRepository.findAll();

            if(historicals.isEmpty()){
                throw new GenericException("No historical found!", HttpStatus.NOT_FOUND);
            }

            return historicals.stream()
                    .map(historical -> HistoricalResponseDto.builder()
                            .idHistorical(historical.getIdHistorical())
                            .dsHistorical(historical.getDsHistorical())
                            .dsReversalHistorical(historical.getDsReversalHistorical())
                            .inAccount(historical.getInAccount())
                            .dtCreatedAt(historical.getDtCreatedAt())
                            .build())
                    .toList();
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

    public HistoricalResponseDto consultHistoricalById(Integer idHistorical) {
        try{

        HistoricalModel historicalModel = historicalRepository.findById(idHistorical)
                .orElseThrow(() -> new GenericException("Historical with id "+idHistorical+" not found!", HttpStatus.NOT_FOUND));

        return HistoricalResponseDto.builder()
                .idHistorical(historicalModel.getIdHistorical())
                .dsHistorical(historicalModel.getDsHistorical())
                .dsReversalHistorical(historicalModel.getDsReversalHistorical())
                .inAccount(historicalModel.getInAccount())
                .dtCreatedAt(historicalModel.getDtCreatedAt())
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

    public AccountResponseDto consultAccountByNrAccount(Integer nrAccount) {
        try{
            AccountModel account = accountRepository.findByNrAccount(nrAccount)
                    .orElseThrow(() -> new GenericException("Account with number "+nrAccount+" not found!", HttpStatus.NOT_FOUND));

            return AccountResponseDto.builder()
                    .cdAgency(account.getCdAgency())
                    .nrAccount(account.getNrAccount())
                    .vlAmount(account.getVlAmount())
                    .dtCreatedAt(account.getDtCreatedAt())
                    .component(ComponentResponseDto.builder()
                                .cdDocument(account.getComponent().getCdDocument())
                                .nmPerson(account.getComponent().getNmPerson())
                                .tpPerson(account.getComponent().getTpPerson())
                                .dsEmail(account.getComponent().getDsEmail())
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

    public List<AccountResponseDto> consultAccounts() {
        try{

            List<AccountModel> accounts = accountRepository.findAll();

            if(accounts.isEmpty()){
                throw new GenericException("No accounts found!", HttpStatus.NOT_FOUND);
            }

            return  accounts.stream()
                    .map(account -> AccountResponseDto.builder()
                            .cdAgency(account.getCdAgency())
                            .nrAccount(account.getNrAccount())
                            .vlAmount(account.getVlAmount())
                            .dtCreatedAt(account.getDtCreatedAt())
                            .component(ComponentResponseDto.builder()
                                    .cdDocument(account.getComponent().getCdDocument())
                                    .nmPerson(account.getComponent().getNmPerson())
                                    .tpPerson(account.getComponent().getTpPerson())
                                    .dsEmail(account.getComponent().getDsEmail())
                                    .build())
                            .build())
                    .toList();
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
