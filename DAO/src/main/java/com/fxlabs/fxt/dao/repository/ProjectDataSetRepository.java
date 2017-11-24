package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDataSetRepository extends JpaRepository<TestSuite, String> {

    List<TestSuite> findByProjectId(String projectId);

    Long countByProjectId(String projectId);
}
