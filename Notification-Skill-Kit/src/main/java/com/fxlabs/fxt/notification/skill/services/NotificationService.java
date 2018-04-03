package com.fxlabs.fxt.notification.skill.services;

import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.fxlabs.fxt.dto.notification.NotificationTask;

/**
 * @author Mohammed Shoukath Ali
 */
public interface NotificationService {

    void process(NotificationTask task);

}

