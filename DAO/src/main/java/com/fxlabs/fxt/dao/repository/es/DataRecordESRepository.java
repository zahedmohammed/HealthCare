package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.DataRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface DataRecordESRepository extends ElasticsearchRepository<DataRecord, String> {

    Stream<DataRecord> findByDataSet(String dataSetId);
}
