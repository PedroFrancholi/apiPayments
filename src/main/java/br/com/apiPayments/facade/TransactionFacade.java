package br.com.apiPayments.facade;

import br.com.apiPayments.enuns.StatusPaymentEnum;
import br.com.apiPayments.exception.GenericException;
import br.com.apiPayments.messaging.TransactionProducer;
import br.com.apiPayments.model.AccountModel;
import br.com.apiPayments.model.HistoricalModel;
import br.com.apiPayments.model.TransactionModel;
import br.com.apiPayments.repository.AccountRepository;
import br.com.apiPayments.repository.HistoricalRepository;
import br.com.apiPayments.repository.TransactionRepository;
import br.com.apiPayments.util.LogUtil;
import br.com.apiPayments.web.dto.request.TransactionRequestDto;
import br.com.apiPayments.web.dto.response.AccountResponseDto;
import br.com.apiPayments.web.dto.response.ComponentResponseDto;
import br.com.apiPayments.web.dto.response.HistoricalResponseDto;
import br.com.apiPayments.web.dto.response.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionFacade {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HistoricalRepository historicalRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionProducer transactionProducer;

    @Transactional
    public TransactionResponseDto createTransaction(TransactionRequestDto transactionBody){
        try{
            AccountModel originAccountModel = accountRepository.findByNrAccount(transactionBody.getNrOriginAccount())
                    .orElseThrow(() -> new GenericException("Origin account with number "+transactionBody.getNrOriginAccount()+" not found!", HttpStatus.NOT_FOUND));

            Optional<AccountModel> destinationAccountModel = accountRepository.findByNrAccount(transactionBody.getNrDestinationAccount());

            HistoricalModel historicalModel = historicalRepository.findById(transactionBody.getCdHistorical())
                    .orElseThrow(() -> new GenericException("Historical with code "+transactionBody.getCdHistorical()+" not found!", HttpStatus.NOT_FOUND));

            if(historicalModel.getInAccount() && transactionBody.getNrDestinationAccount() == null){
                throw new GenericException("Destination account must be provided!", HttpStatus.BAD_REQUEST);
            }

            if(destinationAccountModel.isEmpty() && historicalModel.getInAccount()){
                throw new GenericException("Destination account with number "+transactionBody.getNrDestinationAccount()+" not found!", HttpStatus.NOT_FOUND);
            }

            if(transactionBody.getVlTransaction().compareTo(BigDecimal.ZERO) < 0){
                throw new GenericException("Transaction value is invalid "+transactionBody.getVlTransaction(), HttpStatus.BAD_REQUEST);
            }

            String cdStatusPayment = null;
            BigDecimal vlPayment = transactionBody.getVlTransaction();

            switch (transactionBody.getCdHistorical()){
                case 1,4 -> {
                    funTrasnfer(transactionBody); //Transfer by Accounts | Pix
                    cdStatusPayment = StatusPaymentEnum.PENDING.getValue();
                    vlPayment = vlPayment.multiply(BigDecimal.ONE.negate());
                }
                case 2 -> {
                    funWithdrawMoney(transactionBody); //WithdrawMoney
                    cdStatusPayment = StatusPaymentEnum.EFFECTIVATED.getValue();
                    vlPayment = vlPayment.multiply(BigDecimal.ONE.negate());
                }
                case 3 -> {
                    funDeposit(transactionBody); //Deposit
                    cdStatusPayment = StatusPaymentEnum.EFFECTIVATED.getValue();
                }
                default -> throw new GenericException("The application cannot process this historical with code: "+transactionBody.getCdHistorical()+".", HttpStatus.BAD_REQUEST);
            }

            if (cdStatusPayment == null) {
                throw new GenericException("Status not defined", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            TransactionModel transactionModel = TransactionModel.builder()
                    .origin(originAccountModel)
                    .vlTransaction(vlPayment)
                    .historical(historicalModel)
                    .cdStatus(cdStatusPayment)
                    .inReversal(false)
                    .dtCreatedAt(LocalDateTime.now())
                    .dtProcessAt(LocalDateTime.now())
                    .destination(historicalModel.getInAccount() ? destinationAccountModel.get() :null)
                    .dsDetail(transactionBody.getDsDetail())
                    .build();

            transactionRepository.save(transactionModel);

            if(transactionModel.getCdStatus().equals(StatusPaymentEnum.PENDING.getValue())){
                transactionProducer.sendTransaction(transactionModel.getCdTransaction());
            }

            AccountModel originAccountAfterModel = accountRepository.findByNrAccount(transactionBody.getNrOriginAccount()).get();

            AccountModel destinationAccountAfterModel = accountRepository.findByNrAccount(transactionBody.getNrDestinationAccount()).orElse(null);

            return TransactionResponseDto.builder()
                            .originAccount(AccountResponseDto.builder()
                                                .cdAgency(transactionModel.getOrigin().getCdAgency())
                                                .nrAccount(transactionModel.getOrigin().getNrAccount())
                                                .vlAmount(originAccountAfterModel.getVlAmount())
                                                .component(ComponentResponseDto.builder()
                                                    .nmPerson(transactionModel.getOrigin().getComponent().getNmPerson())
                                                    .build())
                                            .build())
                            .destinationAccount(historicalModel.getInAccount() ? AccountResponseDto.builder()
                                                .cdAgency(transactionModel.getDestination().getCdAgency())
                                                .nrAccount(transactionModel.getDestination().getNrAccount())
                                                .vlAmount(destinationAccountAfterModel.getVlAmount())
                                                .component(ComponentResponseDto.builder()
                                                    .nmPerson(transactionModel.getDestination().getComponent().getNmPerson())
                                                    .build())
                                            .build() : null)
                            .vlTransaction(transactionBody.getVlTransaction())
                            .historical(HistoricalResponseDto.builder()
                                                .dsHistorical(transactionModel.getHistorical().getDsHistorical())
                                            .build())
                            .cdStatus(transactionModel.getCdStatus())
                            .dsDetail(transactionModel.getDsDetail())
                            .dtCreatedAt(transactionModel.getDtCreatedAt())
                            .dtProcessedAt(transactionModel.getDtProcessAt())
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

    private void funTrasnfer(TransactionRequestDto transactionBody) {
        AccountModel originAccountModel = accountRepository.findByNrAccount(transactionBody.getNrOriginAccount()).get();

        AccountModel destinationAccountModel = accountRepository.findByNrAccount(transactionBody.getNrDestinationAccount()).get();

        if(originAccountModel.getVlAmount().subtract(transactionBody.getVlTransaction()).compareTo(BigDecimal.ZERO) < 0){
            throw new GenericException("Insufficient amount for the transaction. Balance amount: "+originAccountModel.getVlAmount(), HttpStatus.BAD_REQUEST);
        }

        originAccountModel.setVlAmount(originAccountModel.getVlAmount().subtract(transactionBody.getVlTransaction()));
        originAccountModel.setDtLastUpdate(LocalDateTime.now());

        destinationAccountModel.setVlAmount(destinationAccountModel.getVlAmount().add(transactionBody.getVlTransaction()));
        destinationAccountModel.setDtLastUpdate(LocalDateTime.now());

        accountRepository.save(originAccountModel);
        accountRepository.save(destinationAccountModel);
    }

    private void funWithdrawMoney(TransactionRequestDto transactionBody) {
        AccountModel originAccountModel = accountRepository.findByNrAccount(transactionBody.getNrOriginAccount()).get();

        if(originAccountModel.getVlAmount().subtract(transactionBody.getVlTransaction()).compareTo(BigDecimal.ZERO) < 0){
            throw new GenericException("Insufficient amount for withdraw. Balance amount: "+originAccountModel.getVlAmount(), HttpStatus.BAD_REQUEST);
        }

        originAccountModel.setVlAmount(originAccountModel.getVlAmount().subtract(transactionBody.getVlTransaction()));
        originAccountModel.setDtLastUpdate(LocalDateTime.now());

        accountRepository.save(originAccountModel);
    }

    private void funDeposit(TransactionRequestDto transactionBody) {
        AccountModel originAccountModel = accountRepository.findByNrAccount(transactionBody.getNrOriginAccount()).get();

        originAccountModel.setVlAmount(originAccountModel.getVlAmount().add(transactionBody.getVlTransaction()));
        originAccountModel.setDtLastUpdate(LocalDateTime.now());

        accountRepository.save(originAccountModel);
    }
}
