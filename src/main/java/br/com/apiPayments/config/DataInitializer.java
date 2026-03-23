package br.com.apiPayments.config;

import br.com.apiPayments.model.AccountModel;
import br.com.apiPayments.model.ComponentModel;
import br.com.apiPayments.model.HistoricalModel;
import br.com.apiPayments.repository.AccountRepository;
import br.com.apiPayments.repository.ComponentRepository;
import br.com.apiPayments.repository.HistoricalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final HistoricalRepository historicalRepository;
    private final ComponentRepository componentRepository;
    private final AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        loadHistorical();
        loadComponent();
        loadAccount();
    }

    private void loadHistorical() {
        if(historicalRepository.count() == 0){
            historicalRepository.saveAll(List.of(
                    HistoricalModel.builder()
                            .dsHistorical("Transfer by accounts")
                            .dsReversalHistorical("Reversal Transfer by accounts")
                            .inAccount(true)
                            .dtCreatedAt(LocalDateTime.now())
                            .build(),
                    HistoricalModel.builder()
                            .dsHistorical("Withdraw money")
                            .dsReversalHistorical("Reversal Withdraw money")
                            .inAccount(false)
                            .dtCreatedAt(LocalDateTime.now())
                            .build(),
                    HistoricalModel.builder()
                            .dsHistorical("Deposit")
                            .dsReversalHistorical("Reversal Deposit")
                            .inAccount(false)
                            .dtCreatedAt(LocalDateTime.now())
                            .build(),
                    HistoricalModel.builder()
                            .dsHistorical("Pix")
                            .dsReversalHistorical("Reversal Pix")
                            .inAccount(true)
                            .dtCreatedAt(LocalDateTime.now())
                            .build()
            ));
        }
    }

    private void loadComponent(){
        if(componentRepository.count() == 0){
            componentRepository.saveAll(List.of(
               ComponentModel.builder()
                       .nmPerson("Component Test 1")
                       .cdDocument("12345678900")
                       .tpPerson("F")
                       .dsEmail("test1@email.com")
                       .dsPassword("test1")
                       .dtCreatedAt(LocalDateTime.now())
                       .build(),
               ComponentModel.builder()
                        .nmPerson("Component Test 2")
                        .cdDocument("12345676399")
                        .tpPerson("F")
                        .dsEmail("test2@email.com")
                        .dsPassword("test2")
                        .dtCreatedAt(LocalDateTime.now())
                        .build(),
               ComponentModel.builder()
                        .nmPerson("Component Test 3")
                        .cdDocument("12345678901234")
                        .tpPerson("J")
                        .dsEmail("test3@email.com")
                        .dsPassword("test3")
                        .dtCreatedAt(LocalDateTime.now())
                        .build()
            ));
        }
    }

    private void loadAccount(){
        if(accountRepository.count() == 0){
            ComponentModel component1 = componentRepository.findByCdDocument("12345678900")
            .orElseThrow(() -> new RuntimeException("Component not found"));

            ComponentModel component2 = componentRepository.findByCdDocument("12345676399")
                    .orElseThrow(() -> new RuntimeException("Component not found"));

            accountRepository.saveAll(List.of(
                    AccountModel.builder()
                            .nrAccount(1)
                            .cdAgency(1)
                            .vlAmount(BigDecimal.ZERO)
                            .component(component1)
                            .dtCreatedAt(LocalDateTime.now())
                            .dtLastUpdate(LocalDateTime.now())
                            .build(),
                    AccountModel.builder()
                            .nrAccount(2)
                            .cdAgency(2)
                            .vlAmount(BigDecimal.ZERO)
                            .component(component2)
                            .dtCreatedAt(LocalDateTime.now())
                            .dtLastUpdate(LocalDateTime.now())
                            .build()
            ));
        }
    }
}
