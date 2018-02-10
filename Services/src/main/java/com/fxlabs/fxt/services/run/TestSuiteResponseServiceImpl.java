package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.TestSuiteResponseConverter;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteResponseRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class TestSuiteResponseServiceImpl extends GenericServiceImpl<TestSuiteResponse, com.fxlabs.fxt.dto.run.TestSuiteResponse, String> implements TestSuiteResponseService {


    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public TestSuiteResponseServiceImpl(TestSuiteResponseRepository repository, TestSuiteResponseConverter converter,
                                        ElasticsearchTemplate elasticsearchTemplate) {
        super(repository, converter);
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public Long passedSum(String runId) {
        String FIELD = "totalPassed";
        return calcSum(runId, FIELD);
    }

    public Long failedSum(String runId) {
        String FIELD = "totalFailed";
        return calcSum(runId, FIELD);
    }

    public Long timeSum(String runId) {
        String FIELD = "requestTime";
        return calcSum(runId, FIELD);
    }

    private Long calcSum(String runId, String FIELD) {
        Long val = 0L;
        try {
            String KEY = "key";
            SumAggregationBuilder aggregation = AggregationBuilders.sum(KEY)
                    .field(FIELD);

            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withIndices("fxtestsuiteresponse").withTypes("testsuiteresponse")
                    .withQuery(matchQuery("runId", runId).operator(Operator.AND))
                    .withSearchType(SearchType.DEFAULT)
                    .addAggregation(aggregation)
                    .build();
            // when
            Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
                @Override
                public Aggregations extract(SearchResponse response) {
                    return response.getAggregations();
                }
            });
            // then
            Map<String, Aggregation> map = aggregations.asMap();

            Sum sum = (Sum) map.get(KEY);
            Double value = sum.getValue();
            if (value != null) {
                val = value.longValue();
            }
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
        return val;
    }


    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }
}
