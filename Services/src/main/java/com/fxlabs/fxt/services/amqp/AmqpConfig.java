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

    // Bot-Default-Queue
    @Bean(name = "botDefaultQueue")
    public Queue botDefaultQueue(@Value("${fx.default.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding botDefaultQueueBinding(@Value("${fx.default.queue.routingkey}") String routingKey, @Qualifier("botDefaultQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // Response-Queue
    @Bean(name = "botResponseQueue")
    public Queue botResponseQueue(@Value("${fx.default.response.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding botResponseQueueBinding(@Value("${fx.default.response.queue.routingkey}") String routingKey, @Qualifier("botResponseQueue") Queue queue, TopicExchange exchange) {
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


    // GaaS-Queue
    @Bean(name = "gaaSQueue")
    public Queue gaaSQueue(@Value("${fx.gaas.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding gaaSQueueBinding(@Value("${fx.gaas.queue.routingkey}") String routingKey, @Qualifier("gaaSQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // GaaS-Response-Queue
    @Bean(name = "gaaSResponseQueue")
    public Queue gaaSResponseQueue(@Value("${fx.gaas.response.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding gaaSResponseQueueBinding(@Value("${fx.gaas.response.queue.routingkey}") String routingKey, @Qualifier("gaaSResponseQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer gaaSContainer(ConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter,
                                                 @Value("${fx.gaas.response.queue}") String queueName) {

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

    // NaaS-Queue
    @Bean(name = "naaSQueue")
    public Queue naaSQueue(@Value("${fx.naas.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding naaSQueueBinding(@Value("${fx.naas.queue.routingkey}") String routingKey, @Qualifier("naaSQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // NaaS-Response-Queue
    @Bean(name = "naaSResponseQueue")
    public Queue naaSResponseQueue(@Value("${fx.naas.response.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding naaSResponseQueueBinding(@Value("${fx.naas.response.queue.routingkey}") String routingKey, @Qualifier("naaSResponseQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer naaSContainer(ConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter,
                                                 @Value("${fx.naas.response.queue}") String queueName) {

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

    // BaaS-Queue
    @Bean(name = "baaSQueue")
    public Queue baaSQueue(@Value("${fx.baas.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding baaSQueueBinding(@Value("${fx.baas.queue.routingkey}") String routingKey, @Qualifier("baaSQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // NaaS-Response-Queue
    @Bean(name = "baaSResponseQueue")
    public Queue baaSResponseQueue(@Value("${fx.baas.response.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding baaSResponseQueueBinding(@Value("${fx.baas.response.queue.routingkey}") String routingKey, @Qualifier("baaSResponseQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer baaSContainer(ConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter,
                                                 @Value("${fx.naas.response.queue}") String queueName) {

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

    // ITaaS-Queue

    // GaaS-Response-Queue
    @Bean(name = "iTaaSResponseQueue")
    public Queue iTaaSResponseQueue(@Value("${fx.itaas.response.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding iTaaSResponseQueueBinding(@Value("${fx.itaas.response.queue.routingkey}") String routingKey, @Qualifier("iTaaSResponseQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer iTaaSContainer(ConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter,
                                                 @Value("${fx.itaas.response.queue}") String queueName) {

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
    // -- IT Github
    @Bean(name = "iTaaSGithubQueue")
    public Queue iTaaSGitHubQueue(@Value("${fx.itaas.github.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding iTaaSGitHubQueueBinding(@Value("${fx.itaas.github.queue.routingkey}") String routingKey, @Qualifier("iTaaSGithubQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- IT Jira
    @Bean(name = "iTaaSJiraQueue")
    public Queue iTaaSJiraQueue(@Value("${fx.itaas.jira.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding iTaaSJiraQueueBinding(@Value("${fx.itaas.jira.queue.routingkey}") String routingKey, @Qualifier("iTaaSJiraQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


}
