package com.fxlabs.fxt.services.amqp.sender;

import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.task.EmailTask;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class AmqpClientServiceImpl implements AmqpClientService {

    private AmqpTemplate template;
    private String exchange;


    @Autowired
    public AmqpClientServiceImpl(AmqpTemplate template, @Value("${fx.exchange}") String exchange) {
        this.template = template;
        this.exchange = exchange;
    }


    @Override
    public void sendTask(BotTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public void sendTask(GitTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public void sendTask(EmailTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }
}
