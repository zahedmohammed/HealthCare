package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.DataRecord;
import com.fxlabs.fxt.dao.entity.project.DataSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface DataRecordESRepository extends ElasticsearchRepository<DataRecord, String> {

    //Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);

//    Long countByProjectIdAndTypeAndTagsIn(String projectId, String type, List<String> tags);

//    Long countByProjectIdAndType(String projectId, String type);

    Long countByProjectId(String projectId);

//    Stream<DataRecord> findByProjectIdAndNameIn(String projectId, List<String> name);
//
//    Optional<DataRecord> findByProjectIdAndName(String projectId, String name);
}
