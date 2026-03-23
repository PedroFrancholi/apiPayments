package br.com.apiPayments.facade;

import br.com.apiPayments.enuns.StatusPaymentEnum;
import br.com.apiPayments.exception.GenericException;
import br.com.apiPayments.model.AccountModel;
import br.com.apiPayments.model.ComponentModel;
import br.com.apiPayments.model.HistoricalModel;
import br.com.apiPayments.model.TransactionModel;
import br.com.apiPayments.repository.AccountRepository;
import br.com.apiPayments.repository.ComponentRepository;
import br.com.apiPayments.repository.HistoricalRepository;
import br.com.apiPayments.repository.TransactionRepository;
import br.com.apiPayments.util.LogUtil;
import br.com.apiPayments.web.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ConsultFacade {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private HistoricalRepository historicalRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

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
                    .dtLastUpdate(account.getDtLastUpdate())
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
                            .dtLastUpdate(account.getDtLastUpdate())
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

    public List<TransactionResponseDto> consultTransactions() {
        try{
            List<TransactionModel> transactions = transactionRepository.findAll();

            if(transactions.isEmpty()){
                throw new GenericException("No transactions found!", HttpStatus.NOT_FOUND);
            }

            return transactions.stream().map(
                    transaction -> TransactionResponseDto.builder()
                    .cdTransaction(transaction.getCdTransaction())
                    .originAccount(AccountResponseDto.builder()
                            .cdAgency(transaction.getOrigin().getCdAgency())
                            .nrAccount(transaction.getOrigin().getNrAccount())
                            .component(ComponentResponseDto.builder()
                                    .nmPerson(transaction.getOrigin().getComponent().getNmPerson())
                                    .build())
                            .build())
                    .destinationAccount(transaction.getHistorical().getInAccount() ? AccountResponseDto.builder()
                            .cdAgency(transaction.getDestination().getCdAgency())
                            .nrAccount(transaction.getDestination().getNrAccount())
                            .component(ComponentResponseDto.builder()
                                    .nmPerson(transaction.getDestination().getComponent().getNmPerson())
                                    .build())
                            .build() : null)
                    .vlTransaction(transaction.getVlTransaction())
                    .historical(HistoricalResponseDto.builder()
                            .dsHistorical(transaction.getHistorical().getDsHistorical())
                            .build())
                    .cdStatus(transaction.getCdStatus())
                    .dsDetail(transaction.getDsDetail())
                    .dtCreatedAt(transaction.getDtCreatedAt())
                    .dtProcessedAt(transaction.getDtProcessAt())
                    .build()
            ).toList();
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

    public TransactionAccountResponseDto consultTransactionByNrOriginAccount(Integer nrOriginAccount) {
        try{
            AccountModel account = accountRepository.findByNrAccount(nrOriginAccount)
                    .orElseThrow(() -> new GenericException("Account with number  "+nrOriginAccount+" not found!", HttpStatus.NOT_FOUND));

            List<TransactionModel> transactionByOriginAccount = transactionRepository.findByOrigin(account);

            if(transactionByOriginAccount.isEmpty()){
                throw new GenericException("No transactions found for account with number "+nrOriginAccount+".", HttpStatus.NOT_FOUND);
            }

            List<TransactionResponseDto> transactions = transactionByOriginAccount.stream()
                    .sorted(Comparator.comparing(TransactionModel::getDtCreatedAt))
                    .map(
                    transaction -> TransactionResponseDto.builder()
                            .cdTransaction(transaction.getCdTransaction())
                            .originAccount(AccountResponseDto.builder()
                                    .cdAgency(transaction.getOrigin().getCdAgency())
                                    .nrAccount(transaction.getOrigin().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transaction.getOrigin().getComponent().getNmPerson())
                                            .build())
                                    .build())
                            .destinationAccount(transaction.getHistorical().getInAccount() ? AccountResponseDto.builder()
                                    .cdAgency(transaction.getDestination().getCdAgency())
                                    .nrAccount(transaction.getDestination().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transaction.getDestination().getComponent().getNmPerson())
                                            .build())
                                    .build() : null)
                            .vlTransaction(transaction.getVlTransaction())
                            .historical(HistoricalResponseDto.builder()
                                    .dsHistorical(transaction.getHistorical().getDsHistorical())
                                    .build())
                            .cdStatus(transaction.getCdStatus())
                            .dsDetail(transaction.getDsDetail())
                            .dtCreatedAt(transaction.getDtCreatedAt())
                            .dtProcessedAt(transaction.getDtProcessAt())
                            .build()
            ).toList();

            return TransactionAccountResponseDto.builder()
                    .account(AccountResponseDto.builder()
                            .cdAgency(account.getCdAgency())
                            .nrAccount(account.getNrAccount())
                            .component(ComponentResponseDto.builder()
                                    .nmPerson(account.getComponent().getNmPerson())
                                    .build())
                            .vlAmount(account.getVlAmount())
                            .build())
                    .trasaction(transactions)
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

    public List<TransactionResponseDto> consultTransactionPending() {
        try{
            List<TransactionModel> transactionsPending = transactionRepository.findByCdStatus(StatusPaymentEnum.PENDING.getValue());

            if(transactionsPending.isEmpty()){
                throw new GenericException("There is not pending transfers.", HttpStatus.NOT_FOUND);
            }

            return transactionsPending.stream()
                    .sorted(Comparator.comparing(TransactionModel::getDtCreatedAt))
                    .map(
                    transactionPending -> TransactionResponseDto.builder()
                            .cdTransaction(transactionPending.getCdTransaction())
                            .originAccount(AccountResponseDto.builder()
                                    .cdAgency(transactionPending.getOrigin().getCdAgency())
                                    .nrAccount(transactionPending.getOrigin().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transactionPending.getOrigin().getComponent().getNmPerson())
                                            .build())
                                    .build())
                            .destinationAccount(transactionPending.getHistorical().getInAccount() ? AccountResponseDto.builder()
                                    .cdAgency(transactionPending.getDestination().getCdAgency())
                                    .nrAccount(transactionPending.getDestination().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transactionPending.getDestination().getComponent().getNmPerson())
                                            .build())
                                    .build() : null)
                            .vlTransaction(transactionPending.getVlTransaction().abs())
                            .historical(HistoricalResponseDto.builder()
                                    .dsHistorical(transactionPending.getHistorical().getDsHistorical())
                                    .build())
                            .cdStatus(transactionPending.getCdStatus())
                            .dsDetail(transactionPending.getDsDetail())
                            .dtCreatedAt(transactionPending.getDtCreatedAt())
                            .dtProcessedAt(transactionPending.getDtProcessAt())
                            .build()
            ).toList();
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

    public TransactionAccountResponseDto consultTransactionByNrAccount(Integer nrAccount) {
        try{
            AccountModel account = accountRepository.findByNrAccount(nrAccount)
                    .orElseThrow(() -> new GenericException("Account with number  "+nrAccount+" not found!", HttpStatus.NOT_FOUND));

            List<TransactionModel> transactionByOriginAccount = transactionRepository.findByOrigin(account);

            List<TransactionModel> transactionByDestinationAccount = transactionRepository.findByDestination(account);

            if(transactionByOriginAccount.isEmpty() && transactionByDestinationAccount.isEmpty()){
                throw new GenericException("No transactions found for account with number "+nrAccount+".", HttpStatus.NOT_FOUND);
            }

            List<TransactionResponseDto> transactions = new ArrayList<>();

            transactions.addAll(transactionByOriginAccount.stream().map(
                    transaction -> TransactionResponseDto.builder()
                            .cdTransaction(transaction.getCdTransaction())
                            .originAccount(AccountResponseDto.builder()
                                    .cdAgency(transaction.getOrigin().getCdAgency())
                                    .nrAccount(transaction.getOrigin().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transaction.getOrigin().getComponent().getNmPerson())
                                            .build())
                                    .build())
                            .destinationAccount(transaction.getHistorical().getInAccount() ? AccountResponseDto.builder()
                                    .cdAgency(transaction.getDestination().getCdAgency())
                                    .nrAccount(transaction.getDestination().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transaction.getDestination().getComponent().getNmPerson())
                                            .build())
                                    .build() : null)
                            .vlTransaction(transaction.getVlTransaction())
                            .historical(HistoricalResponseDto.builder()
                                    .dsHistorical(transaction.getHistorical().getDsHistorical())
                                    .build())
                            .cdStatus(transaction.getCdStatus())
                            .dsDetail(transaction.getDsDetail())
                            .dtCreatedAt(transaction.getDtCreatedAt())
                            .dtProcessedAt(transaction.getDtProcessAt())
                            .build()
            ).toList());

            transactions.addAll(transactionByDestinationAccount.stream().map(
                    transaction -> TransactionResponseDto.builder()
                            .cdTransaction(transaction.getCdTransaction())
                            .originAccount(AccountResponseDto.builder()
                                    .cdAgency(transaction.getOrigin().getCdAgency())
                                    .nrAccount(transaction.getOrigin().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transaction.getOrigin().getComponent().getNmPerson())
                                            .build())
                                    .build())
                            .destinationAccount(transaction.getHistorical().getInAccount() ? AccountResponseDto.builder()
                                    .cdAgency(transaction.getDestination().getCdAgency())
                                    .nrAccount(transaction.getDestination().getNrAccount())
                                    .component(ComponentResponseDto.builder()
                                            .nmPerson(transaction.getDestination().getComponent().getNmPerson())
                                            .build())
                                    .build() : null)
                            .vlTransaction(transaction.getVlTransaction().multiply(BigDecimal.ONE.negate()))
                            .historical(HistoricalResponseDto.builder()
                                    .dsHistorical(transaction.getHistorical().getDsHistorical())
                                    .build())
                            .cdStatus(transaction.getCdStatus())
                            .dsDetail(transaction.getDsDetail())
                            .dtCreatedAt(transaction.getDtCreatedAt())
                            .dtProcessedAt(transaction.getDtProcessAt())
                            .build()
            ).toList());

            transactions.sort(Comparator.comparing(TransactionResponseDto::getDtCreatedAt));

            return TransactionAccountResponseDto.builder()
                    .account(AccountResponseDto.builder()
                            .cdAgency(account.getCdAgency())
                            .nrAccount(account.getNrAccount())
                            .component(ComponentResponseDto.builder()
                                    .nmPerson(account.getComponent().getNmPerson())
                                    .build())
                            .vlAmount(account.getVlAmount())
                            .build())
                    .trasaction(transactions)
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
