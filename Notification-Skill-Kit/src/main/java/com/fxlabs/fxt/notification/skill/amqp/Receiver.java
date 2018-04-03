package com.fxlabs.fxt.notification.skill.amqp;


import com.fxlabs.fxt.notification.skill.services.NotificationDelegate;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mohammed Shoukath Ali
 */
@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NotificationDelegate delegate;

    public void receiveMessage(NotificationTask task) {
        logger.info("Notification id [{}]", task.getId());
        delegate.process(task);
    }

}