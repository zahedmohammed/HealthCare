package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

public interface TestSuiteRepository extends JpaRepository<TestSuite, String> {

    Stream<TestSuite> findByProjectId(String projectId);

    Long countByProjectId(String projectId);
}
