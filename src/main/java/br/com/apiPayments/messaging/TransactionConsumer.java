package br.com.apiPayments.messaging;

import br.com.apiPayments.config.RabbitMQConfig;
import br.com.apiPayments.enuns.StatusPaymentEnum;
import br.com.apiPayments.model.TransactionModel;
import br.com.apiPayments.repository.TransactionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TransactionConsumer {

    private final TransactionRepository transactionRepository;

    public TransactionConsumer(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRANSACTION)
    public void processTransaction(String cdTransaction) throws InterruptedException {

        TimeUnit.SECONDS.sleep(5);

        TransactionModel transaction = transactionRepository.findById(UUID.fromString(cdTransaction))
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + cdTransaction));

        transaction.setCdStatus(StatusPaymentEnum.EFFECTIVATED.getValue());
        transaction.setDtProcessAt(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}