package com.fxlabs.fxt.vc.git.skill.amqp;

import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.fxlabs.fxt.dto.vc.VCTaskResponse;
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
                  @Value("${fx.caas.response.queue.routingkey}") String routingKey) {

        this.template = template;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }


    public void sendTask(CloudTaskResponse task) {
        this.template.convertAndSend(exchange, routingKey, task);
    }
}
