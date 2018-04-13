package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.DataSet;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface DataSetRepository extends JpaRepository<DataSet, String> {

    Optional<DataSet> findByProjectIdAndName(String projectId, String name);

    //Long countByProjectIdAndInactive(String project, boolean inactive);
}
