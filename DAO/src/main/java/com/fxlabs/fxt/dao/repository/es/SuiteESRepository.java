package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.Suite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SuiteESRepository extends ElasticsearchRepository<Suite, String> {

    Page<Suite> findByRunId(String runId, Pageable pageable);

    Optional<Suite> findById(String id);
}
