package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface JobRepository extends JpaRepository<Job, String> {

    List<Job> findByProjectId(String project, Pageable pageable);

    Stream<Job> findByNextFireBetween(Date start, Date end);

    Stream<Job> findByNextFireLessThan(Date dt);
}
