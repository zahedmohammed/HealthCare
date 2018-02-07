package com.fxlabs.fxt.gaas.services;

import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.dto.git.GitTaskResponse;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.gaas.amqp.Sender;
import com.fxlabs.fxt.sdk.services.CredUtils;
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
        GitTaskResponse response = null;
        try {
            response = gitService.process(task);

            if (response.isSuccess() && !StringUtils.equals(response.getGitLastCommit(), task.getGitLastCommit())) {
                CredUtils.url.set(task.getFxUrl());
                CredUtils.username.set(task.getProjectUser());
                CredUtils.password.set(task.getProjectGrant());
                Project project = service.load(response.getPath());
                if (project == null) {
                    response.setSuccess(false);
                }
            }
            String driverLogs = new String();
            String gitLogs = new String();
            if (CredUtils.taskLogger.get() != null) {
                driverLogs = CredUtils.taskLogger.get().toString();
            }
            if (response.getLogs() != null) {
                gitLogs = response.getLogs();
            }
            response.setLogs(gitLogs + "\n" + driverLogs);
            logger.info(response.toString());


            sender.sendTask(response);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        } finally {
            if (response != null) {
                this.gitService.deleteRepo(response.getPath());
            }
        }
    }
}
