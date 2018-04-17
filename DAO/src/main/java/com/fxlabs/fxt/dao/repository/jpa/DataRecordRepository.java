package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.DataRecord;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author  Mohammed Shoukath Ali
 */
public interface DataRecordRepository extends JpaRepository<DataRecord, String> {

//    Stream<DataRecord> findByProjectId(String projectId);
//
//    Optional<DataRecord> findByProjectIdAndRecord(String projectId, String record);

}
