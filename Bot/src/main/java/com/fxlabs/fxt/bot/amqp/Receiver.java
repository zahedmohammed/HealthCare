package com.fxlabs.fxt.bot.amqp;

import com.fxlabs.fxt.bot.processor.RestProcessor;
import com.fxlabs.fxt.dto.cloud.PingTask;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.LightWeightBotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestProcessor restProcessor;

    public void receiveMessage(BotTask task) {
        logger.info("Task id [{}] name [{}]", task.getId(), task.getSuiteName());
        restProcessor.process(task);
    }

    public List<BotTask> receiveMessage(LightWeightBotTask task) {
        logger.info("Task id [{}] name [{}]", task.getBotTask().getId(), task.getBotTask().getSuiteName());
        return restProcessor.process(task);
    }

    public String receiveMessage(PingTask pingTask) {
        logger.info("PingTask received...");
        return "Status: Ok!";
    }

}