package com.fxlabs.fxt.services.amqp;

import com.fxlabs.fxt.services.amqp.reciever.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Intesar Shannan Mohammed
 */
@Configuration
public class AmqpConfig {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public TopicExchange exchange(@Value("${fx.exchange}") String exchange) {
        return new TopicExchange(exchange);
    }

    // Default-Queue
    @Bean(name = "botDefaultQueue")
    public Queue defaultQueue(@Value("${fx.default.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding defaultQueueBinding(@Value("${fx.default.queue.routingkey}") String routingKey, @Qualifier("botDefaultQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // Response-Queue
    @Bean(name = "responseQueue")
    public Queue responseQueue(@Value("${fx.default.response.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding responseQueueBinding(@Value("${fx.default.response.queue.routingkey}") String routingKey, @Qualifier("responseQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter,
                                             @Value("${fx.default.response.queue}") String queueName) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setConcurrentConsumers(10);
        container.setMaxConcurrentConsumers(10);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
