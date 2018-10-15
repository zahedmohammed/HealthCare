package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.run.TestCaseResponseConverter;
import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.project.AutoSuggestion;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.entity.project.SuggestionStatus;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.es.AutoSuggestionESRepository;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseITESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.dto.clusters.AccountType;
import com.fxlabs.fxt.dto.it.IssueTracker;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.it.IssueTrackerService;
import com.fxlabs.fxt.services.skills.SkillService;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.text.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional
public class TestCaseResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private TestCaseResponseESRepository testCaseResponseESRepository;
    private TestCaseResponseRepository testCaseResponseRepository;
    private IssueTrackerService skillSubscriptionService;
    private TestCaseResponseConverter converter;
    private AmqpClientService amqpClientService;
    private SkillService skillService;
    private AccountRepository accountRepository;
    private JobRepository jobRepository;
    @Value("${fx.itaas.github.queue.routingkey}")
    private String itaasQueue;
    @Value("${fx.itaas.jira.queue.routingkey}")
    private String itaasJiraQueue;
    @Value("${fx.itaas.fx.queue.routingkey}")
    private String itaasFxQueue;
    private TextEncryptor encryptor;
    private TestCaseResponseITRepository testCaseResponseITRepository;
    private TestCaseResponseITESRepository testCaseResponseITESRepository;
    private RunRepository runRepository;

    private AutoSuggestionRepository autoSuggestionRepository;
    private AutoSuggestionESRepository autoSuggestionESRepository;
    private TestSuiteESRepository testSuiteESRepository;

    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "modifiedDate", "createdDate");


    @Autowired
    public TestCaseResponseProcessor(TestCaseResponseESRepository testCaseResponseESRepository, TestCaseResponseConverter converter,
                                     TestCaseResponseRepository testCaseResponseRepository, AmqpClientService amqpClientService,
                                     JobRepository jobRepository, IssueTrackerService skillSubscriptionService, SkillService skillService, TestCaseResponseITRepository testCaseResponseITRepository,
                                     AccountRepository accountRepository, TextEncryptor encryptor, TestCaseResponseITESRepository testCaseResponseITESRepository, RunRepository runRepository,
                                     AutoSuggestionRepository autoSuggestionRepository, AutoSuggestionESRepository autoSuggestionESRepository, TestSuiteESRepository testSuiteESRepository) {
        this.testCaseResponseESRepository = testCaseResponseESRepository;
        this.testCaseResponseRepository = testCaseResponseRepository;
        this.skillService = skillService;
        this.jobRepository = jobRepository;
        this.skillSubscriptionService = skillSubscriptionService;
        this.converter = converter;
        this.amqpClientService = amqpClientService;
        this.accountRepository = accountRepository;
        this.encryptor = encryptor;
        this.testCaseResponseITESRepository = testCaseResponseITESRepository;
        this.testCaseResponseITRepository = testCaseResponseITRepository;
        this.runRepository = runRepository;
        this.autoSuggestionRepository = autoSuggestionRepository;
        this.autoSuggestionESRepository = autoSuggestionESRepository;
        this.testSuiteESRepository = testSuiteESRepository;
    }

    public void process(List<TestCaseResponse> testCaseResponses) {

        logger.info("TestCaseResponseProcessor and size is [{}]...", testCaseResponses.size());
        try {

            if (CollectionUtils.isEmpty(testCaseResponses)) {
                return;
            }

            Iterable<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> result = testCaseResponseRepository.saveAll(converter.convertToEntities(testCaseResponses));
            result = testCaseResponseESRepository.saveAll(result);

            testCaseResponses = converter.convertToDtos(IteratorUtils.toList(result.iterator()));

            AtomicInteger validations = new AtomicInteger();

            testCaseResponses.forEach(tc -> {


                    String key = getKeyForTestCaseResponse(tc);

                    if (StringUtils.isEmpty(key)) {
                        logger.debug("No Issue Tracker Found for project [{}]...", tc.getProject());
                        return;
                    }
                    //Populate issue id
                    populateIssueId(tc);

                if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "fail")) {
                    validations.incrementAndGet();
                    amqpClientService.sendTask(tc, key);
                    addAutoSuggestion(tc);
                }

                if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "pass") && !StringUtils.isEmpty(tc.getIssueId())) {
                    validations.incrementAndGet();
                    amqpClientService.sendTask(tc, key);
                    updateAutoSuggestion(tc);
                }



                // TODO
                // fail-from-na   --> DoneIT-Handler
                // fail-from-na   --> IT-Handler
                // fail-from-pass --> Done IT-Handler
                // pass-from-fail --> IT-Handler
                // fail-from-fail ?
                // pass-from-pass ?
                // pass-from-na   ?
                // send to ITTask to the right Skill.
            });
            String runId = testCaseResponses.get(0).getRunId();
            if (!StringUtils.isEmpty(runId)) {

                Optional<com.fxlabs.fxt.dao.entity.run.Run> runOptional = runRepository.findByRunId(runId);
                if (!runOptional.isPresent()) {
                    return;
                }
                com.fxlabs.fxt.dao.entity.run.Run run = runOptional.get();

                if (run != null && validations.intValue() != 0) {
                    run.setValidations(validations.intValue());
                    runRepository.save(run);
                }
            }




        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }

    private void addAutoSuggestion(TestCaseResponse tcResp) {

        Stream<AutoSuggestion> suggestions = autoSuggestionRepository.findByProjectIdAndTestSuiteNameAndTestCaseNumber(tcResp.getProjectId(), tcResp.getSuite(),tcResp.getTestCase());
        if (suggestions.count() > 0){
            // Issue already exists...
            return;
        }

        TestSuite suite = testSuiteESRepository.findByProjectIdAndName(tcResp.getProjectId(),tcResp.getSuite());

        AutoSuggestion suggestion = new AutoSuggestion();
        suggestion.setSuggestionId(RandomStringUtils.randomAlphanumeric(8));
        suggestion.setProjectId(tcResp.getProjectId());
        suggestion.setStatus(SuggestionStatus.NEW);
        suggestion.setEndPoint(tcResp.getEndpointEval());
        suggestion.setRegion(tcResp.getRegion());
        suggestion.setRespStatusCode(tcResp.getStatusCode());
        suggestion.setTestSuiteId(suite.getId());
        suggestion.setCategory(suite.getCategory().toString());
        suggestion.setSeverity(suite.getSeverity().toString());
        suggestion.setMethod(suite.getMethod().toString());
        suggestion.setTestSuiteName(suite.getName());
        suggestion.setTestCaseNumber(tcResp.getTestCase());

        AutoSuggestion entity = autoSuggestionRepository.saveAndFlush(suggestion);
        autoSuggestionESRepository.save(entity);
    }

    private void updateAutoSuggestion(TestCaseResponse tcResp) {

        Stream<AutoSuggestion> suggestions = autoSuggestionRepository.findByProjectIdAndTestSuiteNameAndTestCaseNumber(tcResp.getProjectId(), tcResp.getSuite(),tcResp.getTestCase());

        suggestions.forEach(suggestion -> {
            suggestion.setStatus(SuggestionStatus.CLOSED);
            AutoSuggestion entity = autoSuggestionRepository.saveAndFlush(suggestion);
            autoSuggestionESRepository.save(entity);
        });

    }

    private String getKeyForTestCaseResponse(TestCaseResponse tc) {

        if (StringUtils.isEmpty(tc.getJobId())) {
            logger.debug("No job id found for TestCaseResponse with project [{}]...", tc.getProject());
            return null;
        }

        Optional<Job> jobOptional = jobRepository.findById(tc.getJobId());

        if (!jobOptional.isPresent()) {
            logger.debug("Job not found for TestCaseResponse with project [{}]...", tc.getProject());
            return null;
        }

        Job job = jobOptional.get();

        if (StringUtils.isEmpty(job.getIssueTracker())) {
            logger.debug("IssueTracker  not found for Job [{}]...", job.getName());
            return null;
        }

//        Response<IssueTracker> issueTrackerResponse = skillSubscriptionService.findByName(job.getIssueTracker().getName(), job.getProject().getOrg().getName());
        Response<com.fxlabs.fxt.dto.project.JobIssueTracker> issueTrackerResponse = skillSubscriptionService.findJobIsseTrackerById(job.getIssueTracker().getId());

        if (issueTrackerResponse.getData() == null || issueTrackerResponse.getData().getAccount() == null) {
            return null;
        }
        //prop1 will have host url
        if (StringUtils.isEmpty(issueTrackerResponse.getData().getUrl()) && !AccountType.FX_Issues.equals(issueTrackerResponse.getData().getAccountType())) {
            return null;
        }

        tc.setIssueTrackerHost(issueTrackerResponse.getData().getUrl());

        Optional<com.fxlabs.fxt.dao.entity.clusters.Account> accountOptional = accountRepository.findById(issueTrackerResponse.getData().getAccount());
        com.fxlabs.fxt.dao.entity.clusters.Account account = accountOptional.isPresent() ? accountOptional.get() : null;

        if (account == null) {
            return null;
        }

        tc.setUsername(account.getAccessKey());
        if (!StringUtils.isEmpty(account.getSecretKey())) {
            tc.setPassword(encryptor.decrypt(account.getSecretKey()));
        }

        //TODO get key from different source

        switch (account.getAccountType()) {
            case GitHub:
                return itaasQueue;
            case Jira:
                tc.setIssueTrackerProjectName(issueTrackerResponse.getData().getProjectKey());
                return itaasJiraQueue;
            case FX_Issues:
                return itaasFxQueue;
        }


        return null;
    }

    private void checkOpenBugs(TestCaseResponse tc) {

        populateIssueId(tc);

        String key = getKeyForTestCaseResponse(tc);

        if (StringUtils.isEmpty(key)) {
            return;
        }


        logger.info("TestCaseResponseProcessor updating issue  [{}]  for project [{}]", tc.getIssueId(), tc.getProject());
        amqpClientService.sendTask(tc, key);


    }


    private void populateIssueId(TestCaseResponse tc) {
        String projectId = tc.getProjectId();
        String jobId = tc.getJobId();
        String testSuite = tc.getSuite();
        String testCase = tc.getTestCase();

        String id = projectId + "//" + jobId + "//" + testSuite + "//" + testCase;

      //  Optional<TestCaseResponseIssueTracker> existingIssue = testCaseResponseITRepository.findByTestCaseResponseIssueTrackerId(id);
        Optional<TestCaseResponseIssueTracker> existingIssue = testCaseResponseITRepository.findByProjectIdAndJobIdAndTestSuiteNameAndTestCaseNumber(projectId,jobId,testSuite,testCase);

        if (!existingIssue.isPresent()) {
            return;
        }

        TestCaseResponseIssueTracker testCaseResponseIssueTracker = existingIssue.get();

        tc.setIssueId(testCaseResponseIssueTracker.getIssueId());
    }
}
