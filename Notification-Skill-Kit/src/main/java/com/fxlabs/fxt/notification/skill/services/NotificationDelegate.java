package com.fxlabs.fxt.notification.skill.services;

import com.fxlabs.fxt.dto.notification.NotificationTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mohammed Shoukath Ali
 */
@Component
public class NotificationDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private NotificationService service;

    public void process(NotificationTask task) {
        logger.info("NotificationTask [{}]", task.getId());
        try {
            service.process(task);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }


}
