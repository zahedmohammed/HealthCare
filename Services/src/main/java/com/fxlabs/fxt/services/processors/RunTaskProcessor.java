package com.fxlabs.fxt.services.processors;

import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.services.sender.BotClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunTaskProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private BotClientService botClientService;

    @Autowired
    public RunTaskProcessor(BotClientService botClientService) {
        this.botClientService = botClientService;
    }

    public void process(Run run) {
        BotTask task = new BotTask();
        task.setId("123");
        logger.info("Sending task [{}] to region [{}]...", task.getId(), run.getProjectJob().getRegion());
        botClientService.sendTask(task, run.getProjectJob().getRegion());
    }
}
