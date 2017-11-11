package com.fxlabs.fxt.bot;

import java.util.concurrent.CountDownLatch;

import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(BotTask task) {
        System.out.println("Received <" + task.getId() + ">");
        logger.info("Task [{}]", task.getId());
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
