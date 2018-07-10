package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.JobIssueTracker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mohammed Shoukath Ali
 */
public interface JobIssueTrackerRepository extends JpaRepository<JobIssueTracker, String> {
}
