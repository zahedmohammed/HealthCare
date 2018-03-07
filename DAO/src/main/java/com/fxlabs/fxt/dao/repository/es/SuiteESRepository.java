package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.Suite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SuiteESRepository extends ElasticsearchRepository<Suite, String> {
    List<Suite> findByRunId(String runId, Pageable pageable);
}
