package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestCaseResponseESRepository extends ElasticsearchRepository<TestCaseResponse, String> {


}
