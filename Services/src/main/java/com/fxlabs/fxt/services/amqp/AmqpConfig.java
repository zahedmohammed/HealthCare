package com.fxlabs.fxt.services.amqp;

import com.fxlabs.fxt.services.amqp.reciever.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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

    /*@Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }*/

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

    // Marketplace-Queue
    @Bean(name = "botMarketplaceQueue")
    public Queue botMarketplaceQueue(@Value("${fx.marketplace.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding botMarketplaceQueueBinding(@Value("${fx.marketplace.queue.routingkey}") String routingKey, @Qualifier("botMarketplaceQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer marketplaceContainer(ConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter,
                                                        @Value("${fx.marketplace.queue}") String queueName) {

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


    // Cloud-as-a-Service-Queue

    @Bean(name = "caaSResponseQueue")
    public Queue caaSResponseQueue(@Value("${fx.caas.response.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caaSResponseQueueBinding(@Value("${fx.caas.response.queue.routingkey}") String routingKey, @Qualifier("caaSResponseQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer caaSContainer(ConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter,
                                                 @Value("${fx.caas.response.queue}") String queueName) {

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

    // -- Cloud-as-a-Service AWS_EC2
    @Bean(name = "caaSAWSEC2Queue")
    public Queue caaSAWSEC2Queue(@Value("${fx.caas.aws_ec2.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caaSAWSEC2QueueBinding(@Value("${fx.caas.aws_ec2.queue.routingkey}") String routingKey, @Qualifier("caaSAWSEC2Queue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service AWS_EC2
    @Bean(name = "caaSAzureQueue")
    public Queue caaSAzureQueue(@Value("${fx.caas.azure.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasAzureQueueBinding(@Value("${fx.caas.azure.queue.routingkey}") String routingKey, @Qualifier("caaSAzureQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service GCP
    @Bean(name = "caaSGCPQueue")
    public Queue caaSGCPQueue(@Value("${fx.caas.gcp.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasGCPQueueBinding(@Value("${fx.caas.gcp.queue.routingkey}") String routingKey, @Qualifier("caaSGCPQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service DO
    @Bean(name = "caaSDOQueue")
    public Queue caaSDOQueue(@Value("${fx.caas.do.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasDOQueueBinding(@Value("${fx.caas.do.queue.routingkey}") String routingKey, @Qualifier("caaSDOQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service IBM
    @Bean(name = "caaSIBMQueue")
    public Queue caaSIBMQueue(@Value("${fx.caas.ibm.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasIBMQueueBinding(@Value("${fx.caas.ibm.queue.routingkey}") String routingKey, @Qualifier("caaSIBMQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service Rackspace
    @Bean(name = "caaSRackspaceQueue")
    public Queue caaSRackspaceQueue(@Value("${fx.caas.rackspace.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasRackspaceQueueBinding(@Value("${fx.caas.rackspace.queue.routingkey}") String routingKey, @Qualifier("caaSRackspaceQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service Oracle
    @Bean(name = "caaSOracleQueue")
    public Queue caaSOracleQueue(@Value("${fx.caas.oracle.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasOracleQueueBinding(@Value("${fx.caas.oracle.queue.routingkey}") String routingKey, @Qualifier("caaSOracleQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service vSphere
    @Bean(name = "caaSVsphereQueue")
    public Queue caaSVsphereQueue(@Value("${fx.caas.vsphere.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasVsphereQueueBinding(@Value("${fx.caas.vsphere.queue.routingkey}") String routingKey, @Qualifier("caaSVsphereQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service OpenStack
    @Bean(name = "caaSOSQueue")
    public Queue caaSOSQueue(@Value("${fx.caas.os.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasOSQueueBinding(@Value("${fx.caas.os.queue.routingkey}") String routingKey, @Qualifier("caaSOSQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service Docker-Swarm
    @Bean(name = "caaSDSQueue")
    public Queue caaSDSQueue(@Value("${fx.caas.ds.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasDSQueueBinding(@Value("${fx.caas.ds.queue.routingkey}") String routingKey, @Qualifier("caaSDSQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Cloud-as-a-Service Kubernetes
    @Bean(name = "caaSK8Queue")
    public Queue caaSK8Queue(@Value("${fx.caas.k8.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding caasK8QueueBinding(@Value("${fx.caas.k8.queue.routingkey}") String routingKey, @Qualifier("caaSK8Queue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // -- Slack Notification as a service
    @Bean(name = "notificationSlackQueue")
    public Queue notificationSlackQueue(@Value("${fx.notification.slack.queue}") String queue) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        return new Queue(queue, true, false, false, args);
    }

    @Bean
    public Binding notificationSlackQueueBinding(@Value("${fx.notification.slack.queue.routingkey}") String routingKey, @Qualifier("caaSK8Queue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


}
