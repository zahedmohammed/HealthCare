package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteResponseESRepository extends ElasticsearchRepository<TestSuiteResponse, String> {

    //Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);
}
