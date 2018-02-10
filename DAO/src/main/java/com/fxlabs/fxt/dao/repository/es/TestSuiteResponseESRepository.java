package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Map;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteResponseESRepository extends ElasticsearchRepository<TestSuiteResponse, String> {

    //Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);

    Optional<Long> countByRunId(String runId);

    //@Query("SELECT SUM(totalPassed) FROM TestSuiteResponse WHERE runId like ?1")
    //Optional<Long> sumOfTotalTestsByRunId(String runId);

    @Query("{" +
            "    \"aggs\" : {" +
            "        \"tsr\" : {" +
            "            \"filter\" : { \"term\": { \"runId\": \"?0\" } }," +
            "            \"aggs\" : {" +
            "                \"sum_passed\" : { \"sum\" : { \"field\" : \"totalPassed\" } }" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Map<String, String> aggrResults(String runId);


}
