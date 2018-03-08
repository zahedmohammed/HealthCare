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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Autowired
    public TestCaseResponseProcessor(TestCaseResponseESRepository testCaseResponseESRepository, TestCaseResponseConverter converter,
                                     TestCaseResponseRepository testCaseResponseRepository, AmqpClientService amqpClientService) {
        this.testCaseResponseESRepository = testCaseResponseESRepository;
        this.testCaseResponseRepository = testCaseResponseRepository;
        this.converter = converter;
        this.amqpClientService = amqpClientService;
    }

    public void process(List<TestCaseResponse> testCaseResponses) {
        try {

            Iterable<com.fxlabs.fxt.dao.entity.run.TestCaseResponse> result = testCaseResponseRepository.saveAll(converter.convertToEntities(testCaseResponses));
            result = testCaseResponseESRepository.saveAll(result);

            testCaseResponses = converter.convertToDtos(IteratorUtils.toList(result.iterator()));

            testCaseResponses.forEach(tc -> {
                if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "fail")) {

                    amqpClientService.sendTask(tc, itaasQueue);
                    // TODO
                    // Load skill from job
                    // send the message to skill queue.
                }

                if (org.apache.commons.lang3.StringUtils.equals(tc.getResult(), "pass")) {
                    //TODO
                    testCaseResponseESRepository.
                            findByProjectAndJobAndEnvAndSuiteAndEndpointEvalAndRequestEval(tc.getProject(), tc.getJob(),
                                    tc.getEnv(), tc.getSuite(), tc.getEndpointEval(), tc.getRequestEval());

                    //amqpClientService.sendTask(tc, itaasQueue);
                    // TODO
                    // Load skill from job
                    // send the message to skill queue.
                }

                // TODO
                // fail-from-na   --> IT-Handler
                // fail-from-na   --> IT-Handler
                // fail-from-pass --> IT-Handler
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
}
