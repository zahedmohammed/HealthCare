package com.fxlabs.fxt.services.amqp.sender;

import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.PingTask;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.task.EmailTask;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class AmqpClientServiceImpl implements AmqpClientService {

    private AmqpTemplate template;
    private String exchange;

    private FanoutExchange fanout;

    final private static ParameterizedTypeReference<String> PARAMETERIZED_STRING_REFERENCE = new ParameterizedTypeReference<String>() { };


    @Autowired
    public AmqpClientServiceImpl(AmqpTemplate template, @Value("${fx.exchange}") String exchange, FanoutExchange fanout) {
        this.template = template;
        this.exchange = exchange;
        this.fanout = fanout;
    }


    @Override
    public void sendTask(BotTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public void sendTask(VCTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public void sendTask(EmailTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public void sendTask(TestCaseResponse task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public void sendTask(CloudTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public String sendTask(PingTask task, String region) {
        Object obj = this.template.convertSendAndReceive(exchange, region, task);
        return (String) obj;
    }

    @Override
    public void sendTask(NotificationTask task, String region) {
        this.template.convertAndSend(exchange, region, task);
    }

    @Override
    public void sendEvent(Event event) {
        this.template.convertAndSend(fanout.getName(), "", event);
    }

}
