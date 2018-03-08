package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.run.SuiteConverter;
import com.fxlabs.fxt.converters.run.TestCaseResponseConverter;
import com.fxlabs.fxt.dao.repository.es.SuiteESRepository;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseRepository;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
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
    private TestCaseResponseConverter converter;
    private AmqpClientService amqpClientService;
    @Value("${fx.itaas.github.queue.routingkey}")
    private String itaasQueue;


    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "modifiedDate", "createdDate");


    @Autowired
    public TestCaseResponseProcessor(TestCaseResponseESRepository testCaseResponseESRepository, TestCaseResponseConverter converter,
                                     TestCaseResponseRepository testCaseResponseRepository, AmqpClientService amqpClientService) {
        this.testCaseResponseESRepository = testCaseResponseESRepository;
        this.testCaseResponseRepository = testCaseResponseRepository;
        this.converter = converter;
        this.amqpClientService = amqpClientService;
    }

    public void process(List<TestCaseResponse> testCaseResponses) {

        logger.info("TestCaseResponseProcessor and size is [{}]...", testCaseResponses.size());
        try {

            Iterable<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> result = testCaseResponseRepository.saveAll(converter.convertToEntities(testCaseResponses));
            result = testCaseResponseESRepository.saveAll(result);

            testCaseResponses = converter.convertToDtos(IteratorUtils.toList(result.iterator()));

            testCaseResponses.forEach(tc -> {
                if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "fail")) {

                    getExistingIssueId(tc);

                    amqpClientService.sendTask(tc, itaasQueue);
                    // TODO
                    // Load skill from job
                    // send the message to skill queue.
                }
                //Check past test data for failure
                checkPastDataForFailure(tc);

                // TODO
                // Load skill from job
                // send the message to skill queue.
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

    private void getExistingIssueId(TestCaseResponse tc) {
        List<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> oldtestresult = testCaseResponseESRepository.
                findByProjectAndJobAndSuiteAndTestCase(tc.getProject(), tc.getJob(), tc.getSuite(), tc.getTestCase(),  PageRequest.of(1, 1, DEFAULT_SORT));


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
                    findByProjectAndJobAndSuiteAndTestCase(tc.getProject(), tc.getJob(), tc.getSuite(), tc.getTestCase(),  PageRequest.of(1, 1, DEFAULT_SORT));


            if (!CollectionUtils.isEmpty(oldtestresult) &&
                    org.apache.commons.lang3.StringUtils.equals(oldtestresult.get(0).getResult(), "fail")) {


                if (!StringUtils.isEmpty(oldtestresult.get(0).getIssueId())) {

                    tc.setIssueId(oldtestresult.get(0).getIssueId());

                    logger.info("TestCaseResponseProcessor updating issue  [{}] in for project [{}]", tc.getIssueId(), tc.getProject());
                    amqpClientService.sendTask(tc, itaasQueue);
                }
            }

        }
    }
}
