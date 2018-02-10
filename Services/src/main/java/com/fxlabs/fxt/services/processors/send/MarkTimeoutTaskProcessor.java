package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class MarkTimeoutTaskProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private TestSuiteResponseESRepository testSuiteResponseESRepository;

    @Autowired
    private TestSuiteResponseService testSuiteResponseService;

    /**
     * Find Tasks with state 'PROCESSING'
     * If timeout >= NOW
     * Mark task TIMEOUT or add to description
     * Calculate total-time, total-completed, total-failed etc
     */
    public void process() {
        Date dt = DateUtils.addMinutes(new Date(), -30);
        Stream<Run> runs = runRepository.findByTaskStatusAndCreatedDateLessThan(TaskStatus.PROCESSING, dt);

        runs.forEach(run -> {
            try {
                Optional<Long> count = testSuiteResponseESRepository.countByRunId(run.getId());
                run.getTask().setStatus(TaskStatus.TIMEOUT);
                if (count.isPresent() && count.get() >= run.getTask().getTotalTests()) {
                    // count total-suites
                    // count total-tests
                    // count total-fail
                    // count total-time
                    run.getTask().setTotalSuiteCompleted(count.get());
                    run.getTask().setFailedTests(testSuiteResponseService.failedSum(run.getId()));
                    run.getTask().setTotalTestCompleted(testSuiteResponseService.passedSum(run.getId()));
                    run.getTask().setTotalTime(testSuiteResponseService.timeSum(run.getId()));
                }
                runRepository.saveAndFlush(run);
                // TODO - Test-Suites
            } catch (RuntimeException ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }
        });
    }
}
