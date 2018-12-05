package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.branch.Branch;
import com.fxlabs.issues.dao.entity.project.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, String> {


}