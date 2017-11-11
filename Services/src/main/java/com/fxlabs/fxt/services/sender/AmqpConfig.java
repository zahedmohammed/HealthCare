package com.fxlabs.fxt.services.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AmqpConfig {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public TopicExchange exchange(@Value("${fx.exchange}") String exchange) {
        return new TopicExchange(exchange);
    }

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
}
