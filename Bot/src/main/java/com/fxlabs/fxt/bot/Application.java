package com.fxlabs.fxt.bot;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    /*@Bean
    Queue queue(@Value("${fx.default.queue}") String queue) {
        return new Queue(queue, false);
    }

    @Bean
    TopicExchange exchange(@Value("${fx.exchange}") String exchange) {
        return new TopicExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange, @Value("${fx.default.queue}") String queueName) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }*/

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter,
                                             @Value("${fx.default.queue}") String queueName) {

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

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}