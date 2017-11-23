package com.fxlabs.fxt.services.processors;

import com.fxlabs.fxt.dao.entity.project.ProjectDataSet;
import com.fxlabs.fxt.dao.repository.ProjectDataSetRepository;
import com.fxlabs.fxt.dto.project.ProjectCredential;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.services.amqp.sender.BotClientService;
import com.fxlabs.fxt.services.project.ProjectDataSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RunTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private BotClientService botClientService;
    private ProjectDataSetRepository projectDataSetRepository;

    @Autowired
    public RunTaskRequestProcessor(BotClientService botClientService, ProjectDataSetRepository projectDataSetRepository) {
        this.botClientService = botClientService;
        this.projectDataSetRepository = projectDataSetRepository;
    }

    public void process(Run run) {
        BotTask task = new BotTask();
        task.setId(run.getId());


        logger.info("Sending task [{}] to region [{}]...", task.getId(), run.getProjectJob().getRegion());

        List<ProjectDataSet> list = projectDataSetRepository.findByProjectId(run.getProjectJob().getProject().getId());
        for (ProjectDataSet ds : list) {

            task.setMethod(ds.getMethod());

            copyHeaders(task, ds);

            copyRequests(task, ds);

            copyAuth(run, task, ds);

            copyAssertions(task, ds);

            task.setEndpoint(run.getProjectJob().getProjectEnvironment().getBaseUrl() + ds.getEndpoint());

            botClientService.sendTask(task, run.getProjectJob().getRegion());
        }
    }

    private void copyAssertions(BotTask task, ProjectDataSet ds) {
        // TODO - JPA lazy-load work-around
        List<String> assertions = new ArrayList<>();
        for (String assertion : ds.getAssertions()) {
            assertions.add(assertion);
        }
        task.setAssertions(assertions);
    }

    private void copyHeaders(BotTask task, ProjectDataSet ds) {
        // TODO - JPA lazy-load work-around
        List<String> headers = new ArrayList<>();
        for (String header : ds.getHeaders()) {
            headers.add(header);
        }
        task.setHeaders(headers);
    }

    private void copyRequests(BotTask task, ProjectDataSet ds) {
        // TODO - JPA lazy-load work-around
        List<String> requests = new ArrayList<>();
        for (String request : ds.getRequest()) {
            requests.add(request);
        }
        task.setRequest(requests);
    }

    private void copyAuth(Run run, BotTask task, ProjectDataSet ds) {
        // if empty resolves it to Default
        // if NONE resolves it to none.
        // if value then finds and injects
        List<ProjectCredential> creds = run.getProjectJob().getProjectEnvironment().getCredentials();
        if (StringUtils.isEmpty(ds.getAuth())) {
            for (ProjectCredential cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), "default")) {
                    task.setAuthType(cred.getMethod());
                    task.setUsername(cred.getUsername());
                    task.setPassword(cred.getPassword());
                }
            }
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(ds.getAuth(), "none")) {
            // don't send auth
        } else {
            for (ProjectCredential cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), ds.getAuth())) {
                    task.setAuthType(cred.getMethod());
                    task.setUsername(cred.getUsername());
                    task.setPassword(cred.getPassword());
                }
            }
        }
    }
}
