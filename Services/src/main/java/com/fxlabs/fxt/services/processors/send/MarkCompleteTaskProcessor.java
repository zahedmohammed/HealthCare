package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

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
                Optional<Long> count = testSuiteResponseESRepository.countByRunId(run.getId());
                if (count.isPresent() && count.get() >= run.getTask().getTotalTests()) {
                    run.getTask().setStatus(TaskStatus.COMPLETED);
                    // count total-suites
                    // count total-tests
                    // count total-fail
                    // count total-time
                    run.getTask().setTotalSuiteCompleted(count.get());

                    run.getTask().setFailedTests(testSuiteResponseService.failedSum(run.getId()));
                    run.getTask().setTotalTestCompleted(testSuiteResponseService.passedSum(run.getId()));
                    run.getTask().setTotalTime(testSuiteResponseService.timeSum(run.getId()));

                    runRepository.saveAndFlush(run);

                }
                // TODO - Test-Suites
            } catch (RuntimeException ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }
        });
    }
}
