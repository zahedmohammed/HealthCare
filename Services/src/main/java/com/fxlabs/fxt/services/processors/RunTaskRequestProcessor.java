package com.fxlabs.fxt.services.processors;

import com.fxlabs.fxt.dao.entity.project.Auth;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.services.amqp.sender.BotClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RunTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private BotClientService botClientService;
    private TestSuiteRepository projectDataSetRepository;
    private RunRepository runRepository;

    @Autowired
    public RunTaskRequestProcessor(BotClientService botClientService, TestSuiteRepository projectDataSetRepository,
                                   RunRepository runRepository) {
        this.botClientService = botClientService;
        this.projectDataSetRepository = projectDataSetRepository;
        this.runRepository = runRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void process() {

        //logger.info("started...");
        List<com.fxlabs.fxt.dao.entity.run.Run> runs = runRepository.findByStatus("WAITING");

        for (com.fxlabs.fxt.dao.entity.run.Run run : runs) {

            run.getTask().setStatus("PROCESSING");
            runRepository.save(run);

            BotTask task = new BotTask();
            task.setId(run.getId());

            Environment env = null;
            for (Environment environment : run.getJob().getProject().getEnvironments()) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(run.getJob().getEnvironment(), environment.getName())) {
                    env = environment;
                    break;
                }
            }


            logger.info("Sending task [{}] to region [{}]...", task.getId(), run.getJob().getRegion());

            List<TestSuite> list = projectDataSetRepository.findByProjectId(run.getJob().getProject().getId());
            for (TestSuite ds : list) {

                task.setProjectDataSetId(ds.getId());

                task.setMethod(ds.getMethod());

                copyHeaders(task, ds);

                copyRequests(task, ds);

                copyAuth(run, env, task, ds);

                copyAssertions(task, ds);

                task.setEndpoint(env.getBaseUrl() + ds.getEndpoint());

                botClientService.sendTask(task, run.getJob().getRegion());

            }
        }
    }

    private void copyAssertions(BotTask task, TestSuite ds) {
        // TODO - JPA lazy-load work-around
        List<String> assertions = new ArrayList<>();
        for (String assertion : ds.getAssertions()) {
            assertions.add(assertion);
        }
        task.setAssertions(assertions);
    }

    private void copyHeaders(BotTask task, TestSuite ds) {
        // TODO - JPA lazy-load work-around
        List<String> headers = new ArrayList<>();
        for (String header : ds.getHeaders()) {
            headers.add(header);
        }
        task.setHeaders(headers);
    }

    private void copyRequests(BotTask task, TestSuite ds) {
        // TODO - JPA lazy-load work-around
        List<String> requests = new ArrayList<>();
        for (String request : ds.getRequest()) {
            requests.add(request);
        }
        task.setRequest(requests);
    }

    private void copyAuth(Run run, Environment env, BotTask task, TestSuite ds) {
        // if empty resolves it to Default
        // if NONE resolves it to none.
        // if value then finds and injects
        List<Auth> creds = env.getAuths();
        if (StringUtils.isEmpty(ds.getAuth())) {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), "default")) {
                    task.setAuthType(cred.getAuthType());
                    task.setUsername(cred.getUsername());
                    task.setPassword(cred.getPassword());
                }
            }
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(ds.getAuth(), "none")) {
            // don't send auth
        } else {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), ds.getAuth())) {
                    task.setAuthType(cred.getAuthType());
                    task.setUsername(cred.getUsername());
                    task.setPassword(cred.getPassword());
                }
            }
        }
    }
}
