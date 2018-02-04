package com.fxlabs.fxt.naas.amqp;


import com.fxlabs.fxt.dto.task.EmailTask;
import com.fxlabs.fxt.naas.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmailService emailService;

    public void receiveMessage(EmailTask task) {
        logger.info("Task id [{}] name [{}]", task.getTo(), task.getSubject());
        emailService.send(task.getTo(), task.getSubject(), task.getBody());
    }

}