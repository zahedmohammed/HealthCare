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
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class MarkCompleteTaskProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestSuiteResponseESRepository testSuiteResponseESRepository;

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private TestSuiteResponseService testSuiteResponseService;

    /**
     * Find Tasks with state 'PROCESSING'
     * If completed-suites >= total-suites
     * Mark task complete
     * Calculate total-time, total-completed, total-failed etc
     */
    public void process() {
        Date dt = DateUtils.addMinutes(new Date(), -30);
        Stream<Run> runs = runRepository.findByTaskStatusAndCreatedDateGreaterThan(TaskStatus.PROCESSING, dt);

        runs.forEach(run -> {
            try {
                //Optional<Long> count = testSuiteResponseESRepository.countByRunId(run.getId());
                Long failed = testSuiteResponseService.failedSum(run.getId());
                Long passed = testSuiteResponseService.passedSum(run.getId());
                Long time = testSuiteResponseService.timeSum(run.getId());
                Long bytes = testSuiteResponseService.byteSum(run.getId());

                Long count = failed + passed;
                if (count >= run.getTask().getTotalTests()) {
                    run.getTask().setStatus(TaskStatus.COMPLETED);
                }
                // count total-suites
                // count total-tests
                // count total-fail
                // count total-time
                // count total-bytes
                run.getTask().setTotalSuiteCompleted(count);

                run.getTask().setFailedTests(failed);
                run.getTask().setTotalTestCompleted(passed);
                run.getTask().setTotalTime(time);
                run.getTask().setTotalBytes(bytes);

                runRepository.saveAndFlush(run);

                // TODO - Test-Suites
            } catch (RuntimeException ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }
        });
    }
}
