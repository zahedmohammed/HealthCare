package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface JobRepository extends JpaRepository<Job, String> {

    List<Job> findByProjectIdAndInactive(String project, boolean inactive, Pageable pageable);

    List<Job> findByIssueTrackerName(String issueTracker);

    Long countByProjectIdAndInactive(String project, boolean inactive);

    Stream<Job> findByNextFireBetweenAndInactive(Date start, Date end, boolean inative);

    Stream<Job> findByNextFireLessThanAndInactive(Date dt, boolean inactive);
}
