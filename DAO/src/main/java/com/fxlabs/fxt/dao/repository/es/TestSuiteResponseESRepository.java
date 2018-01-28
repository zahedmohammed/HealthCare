package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSuiteResponseESRepository extends ElasticsearchRepository<TestSuiteResponse, String> {

    //Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);
}
