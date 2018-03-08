package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseESRepository extends ElasticsearchRepository<TestCaseResponse, String> {

    Stream<TestCaseResponse> findByProjectAndJobAndEnvAndSuiteAndEndpointEvalAndRequestEval(String project, String job, String env, String suite, String endpointEval, String requestEval);
}
