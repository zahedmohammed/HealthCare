package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Job;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface JobRepository extends JpaRepository<Job, String> {
}
