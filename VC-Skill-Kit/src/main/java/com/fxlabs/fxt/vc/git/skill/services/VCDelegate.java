package com.fxlabs.fxt.vc.git.skill.services;

//import com.fxlabs.fxt.codegen.code.StubGenerator;
import com.fxlabs.fxt.codegen.code.StubGenerator;
import com.fxlabs.fxt.dto.project.GenPolicy;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.dto.vc.VCTaskResponse;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.sdk.services.BotLogger;
import com.fxlabs.fxt.sdk.services.CredUtils;
import com.fxlabs.fxt.sdk.services.FxCommandService;
import com.fxlabs.fxt.vc.git.skill.amqp.Sender;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class VCDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Sender sender;

    @Autowired
    private VersionControlService versionControlService;

    @Autowired
    private FxCommandService service;

    @Autowired
    private StubGenerator stubGenerator;

    public void process(VCTask task) {
        logger.info("VCTask [{}]", task.getProjectName());
        VCTaskResponse response = null;
        try {
            String path = "/var/lib/fx/" + RandomStringUtils.randomAlphabetic(6);
            Task _task = new Task();

            _task.setVcUrl(task.getVcUrl());
            _task.setVcBranch(task.getVcBranch());
            _task.setVcUsername(task.getVcUsername());
            _task.setVcPassword(task.getVcPassword());
            _task.setVcLastCommit(task.getVcLastCommit());

            response = versionControlService.process(_task, path);
            response.setProjectId(task.getProjectId());
            response.setProjectName(task.getProjectName());

            if (response.isSuccess() && !StringUtils.equals(response.getVcLastCommit(), task.getVcLastCommit())) {

                CredUtils.taskLogger.set(new BotLogger());

                if (task.getGenPolicy() != null && task.getGenPolicy() == GenPolicy.Create) {
                    // TODO Generate tests
                    try {
                        stubGenerator.generate(task.getOpenAPISpec(), path + "/test-suites", null, null);
                        versionControlService.push(path, task.getVcUsername(), task.getVcPassword());
                    } catch (Exception e) {
                        logger.warn(e.getLocalizedMessage(), e);
                        CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "Push", e.getLocalizedMessage());
                    }
                }

                CredUtils.url.set(task.getFxUrl());
                CredUtils.username.set(task.getProjectUser());
                CredUtils.password.set(task.getProjectGrant());

                Project project = service.load(response.getPath(), task.getProjectId());

                response.setSuccess(!BooleanUtils.isTrue(CredUtils.errors.get()));
            }
            String driverLogs = new String();
            String gitLogs = new String();
            if (CredUtils.taskLogger.get() != null) {
                driverLogs = CredUtils.taskLogger.get().getLogs();
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
                FileUtils.deleteQuietly(new File(response.getPath()));
            }
        }
    }


}
