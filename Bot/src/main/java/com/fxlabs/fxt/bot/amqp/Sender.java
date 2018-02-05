package com.fxlabs.fxt.bot.amqp;

import com.fxlabs.fxt.dto.clusters.ClusterPing;
import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class Sender {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private AmqpTemplate template;
    private String exchange;
    private String routingKey;


    @Autowired
    public Sender(AmqpTemplate template,
                  @Value("${fx.exchange}") String exchange,
                  @Value("${fx.default.response.queue.routingkey}") String routingKey) {

        this.template = template;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }


    public void sendTask(BotTask task) {
        this.template.convertAndSend(exchange, routingKey, task);
    }

    public void sendTask(ClusterPing task) {
        this.template.convertAndSend(exchange, routingKey, task);
    }
}
