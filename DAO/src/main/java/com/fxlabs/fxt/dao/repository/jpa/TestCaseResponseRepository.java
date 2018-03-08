package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseRepository extends JpaRepository<TestCaseResponse, String> {

}
