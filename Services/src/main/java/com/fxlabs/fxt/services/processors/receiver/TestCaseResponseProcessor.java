package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.run.SuiteConverter;
import com.fxlabs.fxt.converters.run.TestCaseResponseConverter;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.es.SuiteESRepository;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dao.repository.jpa.SkillSubscriptionRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.dto.skills.Skill;
import com.fxlabs.fxt.dto.skills.SkillSubscription;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.skills.SkillService;
import com.fxlabs.fxt.services.skills.SkillServiceImpl;
import com.fxlabs.fxt.services.skills.SkillSubscriptionService;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class TestCaseResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private TestCaseResponseESRepository testCaseResponseESRepository;
    private TestCaseResponseRepository testCaseResponseRepository;
    private SkillSubscriptionService skillSubscriptionService;
    private TestCaseResponseConverter converter;
    private AmqpClientService amqpClientService;
    private SkillService skillService;
    private JobRepository jobRepository;
    @Value("${fx.itaas.github.queue.routingkey}")
    private String itaasQueue;


    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "modifiedDate", "createdDate");


    @Autowired
    public TestCaseResponseProcessor(TestCaseResponseESRepository testCaseResponseESRepository, TestCaseResponseConverter converter,
                                     TestCaseResponseRepository testCaseResponseRepository, AmqpClientService amqpClientService,
                                     JobRepository jobRepository, SkillSubscriptionService skillSubscriptionService, SkillService skillService) {
        this.testCaseResponseESRepository = testCaseResponseESRepository;
        this.testCaseResponseRepository = testCaseResponseRepository;
        this.skillService = skillService;
        this.jobRepository = jobRepository;
        this.skillSubscriptionService = skillSubscriptionService;
        this.converter = converter;
        this.amqpClientService = amqpClientService;
    }

    public void process(List<TestCaseResponse> testCaseResponses) {

        logger.info("TestCaseResponseProcessor and size is [{}]...", testCaseResponses.size());
        try {

            if (CollectionUtils.isEmpty(testCaseResponses)){
                return;
            }

            Iterable<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> result = testCaseResponseRepository.saveAll(converter.convertToEntities(testCaseResponses));
            result = testCaseResponseESRepository.saveAll(result);

            testCaseResponses = converter.convertToDtos(IteratorUtils.toList(result.iterator()));

            testCaseResponses.forEach(tc -> {
                if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "fail")) {

                    String key =  getKeyForTestCaseResponse(tc);

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

        Response<SkillSubscription> skillSubRespnse = skillSubscriptionService.findByName(job.getIssueTracker());

        if (skillSubRespnse.getData() == null || skillSubRespnse.getData().getSkill() == null) {
            return null;
        }

        if (StringUtils.isEmpty(skillSubRespnse.getData().getProp1())) {
            return null;
        }

        tc.setIssueTrackerHost(skillSubRespnse.getData().getProp1());
        tc.setIssueTrackerProjectName(skillSubRespnse.getData().getProp2());
        tc.setUsername(skillSubRespnse.getData().getCloudAccount().getAccessKey());
        tc.setPassword(skillSubRespnse.getData().getCloudAccount().getSecretKey());
        //TODO get key from different source

        if (skillSubRespnse.getData().getCloudAccount() == null) {
            return null;
        }

        switch(skillSubRespnse.getData().getCloudAccount().getAccountType()){
            case GitHub:
                return itaasQueue;
        }


        return null;
    }

    private void getExistingIssueId(TestCaseResponse tc) {
        List<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> oldtestresult = testCaseResponseESRepository.
                findByProjectAndJobIdAndSuiteAndTestCase(tc.getProject(), tc.getJobId(), tc.getSuite(), tc.getTestCase(),  PageRequest.of(1, 1, DEFAULT_SORT));


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
