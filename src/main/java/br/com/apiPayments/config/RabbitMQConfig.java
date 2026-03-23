package br.com.apiPayments.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_TRANSACTION = "queue.transaction.pendingPayment";
    public static final String QUEUE_TRANSACTION_DLQ = "queue.transaction.pendingPayment.dlq";
    public static final String EXCHANGE_TRANSACTION = "exchange.transaction";
    public static final String EXCHANGE_TRANSACTION_DLQ = "exchange.transaction.dlq";
    public static final String ROUTING_KEY_TRANSACTION = "transaction.pendingPayment";
    public static final String ROUTING_KEY_TRANSACTION_DLQ = "transaction.pendingPayment.dlq";

    @Bean
    public Queue transactionQueue() {
        return QueueBuilder.durable(QUEUE_TRANSACTION)
                .withArgument("x-dead-letter-exchange", EXCHANGE_TRANSACTION_DLQ)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY_TRANSACTION_DLQ)
                .withArgument("x-message-ttl", 60000) //60 segundos
                .build();
    }

    @Bean
    public Queue transactionDlqQueue() {
        return QueueBuilder.durable(QUEUE_TRANSACTION_DLQ).build();
    }

    @Bean
    public DirectExchange transactionExchange() {
        return new DirectExchange(EXCHANGE_TRANSACTION);
    }

    @Bean
    public DirectExchange transactionDlqExchange() {
        return new DirectExchange(EXCHANGE_TRANSACTION_DLQ);
    }

    @Bean
    public Binding transactionBinding(Queue transactionQueue, DirectExchange transactionExchange) {
        return BindingBuilder
                .bind(transactionQueue)
                .to(transactionExchange)
                .with(ROUTING_KEY_TRANSACTION);
    }

    @Bean
    public Binding transactionDlqBinding(Queue transactionDlqQueue, DirectExchange transactionDlqExchange) {
        return BindingBuilder
                .bind(transactionDlqQueue)
                .to(transactionDlqExchange)
                .with(ROUTING_KEY_TRANSACTION_DLQ);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}