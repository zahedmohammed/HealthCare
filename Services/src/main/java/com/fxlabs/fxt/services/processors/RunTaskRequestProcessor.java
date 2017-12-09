package com.fxlabs.fxt.services.processors;

import com.fxlabs.fxt.dao.entity.project.Auth;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.dto.project.HttpMethod;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.services.amqp.sender.BotClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
@Transactional
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

        runs.parallelStream().forEach(run -> {

            run.getTask().setStatus("PROCESSING");
            runRepository.save(run);

            Environment env = findEvn(run);

            AtomicInteger i = new AtomicInteger(1);


            logger.info("Sending task to region [{}]...", run.getJob().getRegion());

            Stream<TestSuite> list = projectDataSetRepository.findByProjectIdAndType(run.getJob().getProject().getId(), TestSuiteType.SUITE);

            //for (TestSuite ds : list) {
            list.forEach(ds -> {
                logger.info("Request {}", i.incrementAndGet());

                BotTask task = new BotTask();
                task.setId(run.getId());
                task.setProjectDataSetId(ds.getId());

                task.setMethod(convert(ds.getMethod()));

                copyHeaders(task, ds);

                copyRequests(task, ds);

                copyAuth(run, env, task, ds);

                copyAssertions(task, ds);

                task.setEndpoint(env.getBaseUrl() + ds.getEndpoint());

                // after
                copyAfter(ds.getAfter(), task, run, env);

                botClientService.sendTask(task, run.getJob().getRegion());

                //}
            });
        });
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

    private HttpMethod convert(com.fxlabs.fxt.dao.entity.project.HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return HttpMethod.GET;
            case DELETE:
                return HttpMethod.DELETE;
            case HEAD:
                return HttpMethod.HEAD;
            case PUT:
                return HttpMethod.PUT;
            case POST:
                return HttpMethod.POST;
            case OPTIONS:
                return HttpMethod.OPTIONS;
            case PATCH:
                return HttpMethod.PATCH;
            case TRACE:
                return HttpMethod.TRACE;
        }
        return null;
    }


    private void copyAfter(List<String> after, BotTask task, Run run, Environment env) {
        if (CollectionUtils.isEmpty(after) || after.isEmpty()) {
            return;
        }

        for (String suite : after) {
            logger.info("Processing after suite [{}]", suite);

            TestSuite suite1 = projectDataSetRepository.findByProjectIdAndTypeAndName(run.getJob().getProject().getId(), TestSuiteType.ABSTRACT, suite);

            logger.info("Suite id [{}]", suite1.getId());

            BotTask afterTask = new BotTask();
            afterTask.setMethod(convert(suite1.getMethod()));

            copyHeaders(afterTask, suite1);

            copyRequests(afterTask, suite1);

            copyAuth(run, env, afterTask, suite1);

            //copyAssertions(task, suite1);

            afterTask.setEndpoint(env.getBaseUrl() + suite1.getEndpoint());

            task.getAfter().add(afterTask);
        }
    }

    private Environment findEvn(Run run) {
        Environment env_ = null;
        for (Environment environment : run.getJob().getProject().getEnvironments()) {
            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(run.getJob().getEnvironment(), environment.getName())) {
                env_ = environment;
                break;
            }
        }

        return env_;
    }

}
