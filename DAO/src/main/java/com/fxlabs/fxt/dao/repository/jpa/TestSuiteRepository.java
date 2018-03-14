package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteRepository extends JpaRepository<TestSuite, String> {

    Stream<TestSuite> findByProjectIdAndType(String projectId, TestSuiteType type);

    Long countByProjectIdAndType(String projectId, TestSuiteType type);

    TestSuite findByProjectIdAndTypeAndName(String projectId, TestSuiteType type, String name);

    Optional<TestSuite> findByProjectIdAndName(String projectId, String name);

    Long countByProjectIdAndInactive(String project, boolean inactive);
}
