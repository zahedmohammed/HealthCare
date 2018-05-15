package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.run.TestCaseResponseConverter;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AccountRepository;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.it.IssueTracker;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.it.IssueTrackerService;
import com.fxlabs.fxt.services.skills.SkillService;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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


    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "modifiedDate", "createdDate");


    @Autowired
    public TestCaseResponseProcessor(TestCaseResponseESRepository testCaseResponseESRepository, TestCaseResponseConverter converter,
                                     TestCaseResponseRepository testCaseResponseRepository, AmqpClientService amqpClientService,
                                     JobRepository jobRepository, IssueTrackerService skillSubscriptionService, SkillService skillService,
                                     AccountRepository accountRepository) {
        this.testCaseResponseESRepository = testCaseResponseESRepository;
        this.testCaseResponseRepository = testCaseResponseRepository;
        this.skillService = skillService;
        this.jobRepository = jobRepository;
        this.skillSubscriptionService = skillSubscriptionService;
        this.converter = converter;
        this.amqpClientService = amqpClientService;
        this.accountRepository = accountRepository;
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

            testCaseResponses.forEach(tc -> {
                if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "fail")) {

                    String key = getKeyForTestCaseResponse(tc);

                    if (StringUtils.isEmpty(key)) {
                        logger.debug("No Issue Tracker Found for project [{}]...", tc.getProject());
                        return;
                    }
                    //Populate issue id
                    getExistingIssueId(tc);

                    amqpClientService.sendTask(tc, key);

                }
                //Check past test data for failure
                checkPastDataForFailure(tc);

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

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
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

        Response<IssueTracker> issueTrackerResponse = skillSubscriptionService.findByName(job.getIssueTracker());

        if (issueTrackerResponse.getData() == null || issueTrackerResponse.getData().getAccount() == null) {
            return null;
        }
        //prop1 will have host url
        if (StringUtils.isEmpty(issueTrackerResponse.getData().getProp1())) {
            return null;
        }

        tc.setIssueTrackerHost(issueTrackerResponse.getData().getProp1());
        tc.setIssueTrackerProjectName(issueTrackerResponse.getData().getProp2());
        tc.setUsername(issueTrackerResponse.getData().getAccount().getAccessKey());

        Optional<com.fxlabs.fxt.dao.entity.clusters.Account> accountOptional = accountRepository.findById(issueTrackerResponse.getData().getAccount().getId());
        com.fxlabs.fxt.dao.entity.clusters.Account account = accountOptional.isPresent() ? accountOptional.get() : null;
        tc.setPassword(account.getSecretKey());

        //TODO get key from different source

        switch (issueTrackerResponse.getData().getAccount().getAccountType()) {
            case GitHub:
                return itaasQueue;
        }


        return null;
    }

    private void getExistingIssueId(TestCaseResponse tc) {
        List<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> oldtestresult = testCaseResponseESRepository.
                findByProjectAndJobIdAndSuiteAndTestCase(tc.getProject(), tc.getJobId(), tc.getSuite(), tc.getTestCase(), PageRequest.of(1, 1, DEFAULT_SORT));


        if (!CollectionUtils.isEmpty(oldtestresult) &&
                !StringUtils.isEmpty(oldtestresult.get(0).getIssueId())) {

            tc.setIssueId(oldtestresult.get(0).getIssueId());
        }
    }

    private void checkPastDataForFailure(TestCaseResponse tc) {

        if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "pass")) {
            //TODO
            //Load latest
            List<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> oldtestresult = testCaseResponseESRepository.
                    findByProjectAndJobAndSuiteAndTestCase(tc.getProject(), tc.getJob(), tc.getSuite(), tc.getTestCase(), PageRequest.of(1, 1, DEFAULT_SORT));


            if (!CollectionUtils.isEmpty(oldtestresult) &&
                    org.apache.commons.lang3.StringUtils.equals(oldtestresult.get(0).getResult(), "fail")) {

                if (StringUtils.isEmpty(oldtestresult.get(0).getIssueId())) {
                    return;
                }

                tc.setIssueId(oldtestresult.get(0).getIssueId());
                String key = getKeyForTestCaseResponse(tc);

                if (StringUtils.isEmpty(key)) {
                    return;
                }
                logger.info("TestCaseResponseProcessor updating issue  [{}]  for project [{}]", tc.getIssueId(), tc.getProject());
                amqpClientService.sendTask(tc, key);
            }

        }
    }
}
