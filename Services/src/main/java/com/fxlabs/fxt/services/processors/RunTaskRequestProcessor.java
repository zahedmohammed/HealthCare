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
        ProjectCredential cred = run.getProjectJob().getProjectEnvironment().getCredentials().get(0);
        logger.info("Sending task [{}] to region [{}]...", task.getId(), run.getProjectJob().getRegion());

        List<ProjectDataSet> list = projectDataSetRepository.findByProjectId(run.getProjectJob().getProject().getId());
        for (ProjectDataSet ds : list) {

            List<String> requests = new ArrayList<>();
            for (String request : ds.getRequest()) {
                requests.add(request);
            }
            task.setRequest(requests);

            task.setMethod(ds.getMethod());
            task.setUsername(cred.getUsername());
            task.setPassword(cred.getPassword());

            List<String> assertions = new ArrayList<>();
            for (String assertion : ds.getAssertions()) {
                assertions.add(assertion);
            }
            task.setAssertions(assertions);

            // TODO - defaults to Basic Auth
            task.setEndpoint(run.getProjectJob().getProjectEnvironment().getBaseUrl() + ds.getEndpoint());
            botClientService.sendTask(task, run.getProjectJob().getRegion());
        }
    }
}
