package com.fxlabs.fxt.bot.amqp;

import com.fxlabs.fxt.bot.processor.RestProcessor;
import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestProcessor restProcessor;

    public void receiveMessage(BotTask task) {
        //System.out.println("Received <" + task.getId() + ">");
        logger.info("Task [{}]", task.getId());

        restProcessor.process(task);
    }

}