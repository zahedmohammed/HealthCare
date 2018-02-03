package com.fxlabs.fxt.gaas.services;

import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.dto.git.GitTaskResponse;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.gaas.amqp.Sender;
import com.fxlabs.fxt.sdk.services.FxCommandService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Sender sender;

    @Autowired
    private GitService gitService;

    @Autowired
    private FxCommandService service;

    public void process(GitTask task) {
        GitTaskResponse response = gitService.process(task);

        if (response.isSuccess() && !StringUtils.equals(response.getGitLastCommit(), task.getGitLastCommit())) {
            Project project = service.load(response.getPath());
            if (project == null) {
                response.setSuccess(false);
            }
        }
        response.setLogs(response.getLogs() + "\n" + service.taskLogger.get().toString());
        logger.info(response.toString());


        sender.sendTask(response);
    }
}
