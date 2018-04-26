package com.fxlabs.fxt.vc.git.skill.services;

//import com.fxlabs.fxt.codegen.code.StubGenerator;

import com.fxlabs.fxt.codegen.code.CodegenThreadUtils;
import com.fxlabs.fxt.codegen.code.StubGenerator;
import com.fxlabs.fxt.dto.project.GenPolicy;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.dto.vc.VCTaskResponse;
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
        String gitPushLogs = StringUtils.EMPTY;
        try {
            String path = "/var/lib/fx/" + RandomStringUtils.randomAlphabetic(6);
            Task _task = new Task();

            _task.setVcUrl(task.getVcUrl());
            _task.setVcBranch(task.getVcBranch());
            _task.setVcUsername(task.getVcUsername());
            _task.setVcPassword(task.getVcPassword());
            _task.setVcLastCommit(task.getVcLastCommit());

            // 1/4. Pull latest from VC
            response = versionControlService.process(_task, path);
            response.setProjectId(task.getProjectId());
            response.setProjectName(task.getProjectName());

            if (response.isSuccess() && !StringUtils.equals(response.getVcLastCommit(), task.getVcLastCommit())) {

                if (task.getGenPolicy() != null && task.getGenPolicy() == GenPolicy.Create) {
                    // TODO Generate tests
                    try {
                        // 2/4. Auto-Code
                        CodegenThreadUtils.taskLogger.set(new com.fxlabs.fxt.codegen.code.BotLogger());
                        stubGenerator.generate(task.getOpenAPISpec(), path + "/test-suites", null, null);
                        // 3/4. Push to VC
                        gitPushLogs = versionControlService.push(path, task.getVcUsername(), task.getVcPassword());
                    } catch (Exception e) {
                        logger.warn(e.getLocalizedMessage(), e);
                        gitPushLogs = e.getLocalizedMessage();
                        //CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "Push", e.getLocalizedMessage());
                    }
                }

                CredUtils.taskLogger.set(new BotLogger());
                CredUtils.url.set(task.getFxUrl());
                CredUtils.username.set(task.getProjectUser());
                CredUtils.password.set(task.getProjectGrant());
                CredUtils.errors.set(Boolean.FALSE);

                // 4/4. Push to Control-Plane
                Project project = service.load(response.getPath(), task.getProjectId());

                response.setSuccess(!BooleanUtils.isTrue(CredUtils.errors.get()));
            }

            String gitPullLogs = new String();
            if (response.getLogs() != null) {
                gitPullLogs = response.getLogs();
            }

            String codegenLogs = new String();
            if (CodegenThreadUtils.taskLogger.get() != null) {
                codegenLogs = CodegenThreadUtils.taskLogger.get().getLogs();
            }

            String controlPlanePushLogs = new String();
            if (CredUtils.taskLogger.get() != null) {
                controlPlanePushLogs = CredUtils.taskLogger.get().getLogs();
            }

            response.setLogs(gitPullLogs + "\n" + codegenLogs + "\n" + gitPushLogs + "\n" + controlPlanePushLogs);
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
