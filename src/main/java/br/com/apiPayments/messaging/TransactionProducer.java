package br.com.apiPayments.messaging;

import br.com.apiPayments.config.RabbitMQConfig;
import br.com.apiPayments.model.TransactionModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionProducer {

    private final RabbitTemplate rabbitTemplate;

    public TransactionProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransaction(UUID cdTransaction) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_TRANSACTION,
                RabbitMQConfig.ROUTING_KEY_TRANSACTION,
                cdTransaction.toString()
        );
    }
}