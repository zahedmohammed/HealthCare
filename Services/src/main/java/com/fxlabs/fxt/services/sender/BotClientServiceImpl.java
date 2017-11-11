package com.fxlabs.fxt.services.sender;

import com.fxlabs.fxt.dto.run.BotTask;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotClientServiceImpl implements BotClientService {

    private AmqpTemplate template;
    private String exchange;


    @Autowired
    public BotClientServiceImpl(AmqpTemplate template, @Value("${fx.exchange}") String exchange) {
        this.template = template;
        this.exchange = exchange;
    }


    @Override
    public void sendTask(BotTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }
}
