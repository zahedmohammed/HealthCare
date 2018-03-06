package com.fxlabs.fxt.it.skill.services;

import com.fxlabs.fxt.dto.it.ITTask;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.sdk.services.FxCommandService;
import com.fxlabs.fxt.vc.git.skill.amqp.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ITDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Sender sender;

    @Autowired
    private IssueTrackerService issueTrackerService;

    @Autowired
    private FxCommandService service;

    public void process(TestCaseResponse task) {
//        VCTaskResponse response = null;
//        try {
//            String path = "/var/lib/fx/" + RandomStringUtils.randomAlphabetic(6);
//            Task _task = new Task();

//            _task.setVcUrl(task.getVcUrl());
//            _task.setVcBranch(task.getVcBranch());
//            _task.setVcUsername(task.getVcUsername());
//            _task.setVcPassword(task.getVcPassword());
//
//            response = versionControlService.process(_task, path);
//            response.setProjectId(task.getProjectId());
//            response.setProjectName(task.getProjectName());
//
//            if (response.isSuccess() && !StringUtils.equals(response.getVcLastCommit(), task.getVcLastCommit())) {
//                CredUtils.url.set(task.getFxUrl());
//                CredUtils.username.set(task.getProjectUser());
//                CredUtils.password.set(task.getProjectGrant());
//                Project project = service.load(response.getPath());
//                if (project == null) {
//                    response.setSuccess(false);
//                }
//            }
//            String driverLogs = new String();
//            String gitLogs = new String();
//            if (CredUtils.taskLogger.get() != null) {
//                driverLogs = CredUtils.taskLogger.get().toString();
//            }
//            if (response.getLogs() != null) {
//                gitLogs = response.getLogs();
//            }
//            response.setLogs(gitLogs + "\n" + driverLogs);
//            logger.info(response.toString());
//
//
//            sender.sendTask(response);
//        } catch (RuntimeException ex) {
//            logger.warn(ex.getLocalizedMessage(), ex);
//        } finally {
//            if (response != null) {
//                FileUtils.deleteQuietly(new File(response.getPath()));
//            }
//        }
    }


}
