package com.fxlabs.fxt.services.amqp.sender;

import com.fxlabs.fxt.dto.run.BotTask;

/**
 * @author Intesar Shannan Mohammed
 */
public interface BotClientService {

    public void sendTask(BotTask task, String region);
}
