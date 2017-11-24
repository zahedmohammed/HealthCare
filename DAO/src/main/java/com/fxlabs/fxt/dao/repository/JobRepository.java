package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String> {
}
