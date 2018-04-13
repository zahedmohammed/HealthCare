package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.DataSet;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface DataSetESRepository extends ElasticsearchRepository<DataSet, String> {

    //Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);

//    Long countByProjectIdAndTypeAndTagsIn(String projectId, String type, List<String> tags);

//    Long countByProjectIdAndType(String projectId, String type);

    Long countByProjectId(String projectId);

    Stream<DataSet> findByProjectIdAndNameIn(String projectId, List<String> name);

    Optional<DataSet> findByProjectIdAndName(String projectId, String name);
}
