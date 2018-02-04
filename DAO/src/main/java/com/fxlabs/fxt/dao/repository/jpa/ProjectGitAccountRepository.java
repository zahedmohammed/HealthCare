package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectGitAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectGitAccountRepository extends JpaRepository<ProjectGitAccount, String> {
    Optional<ProjectGitAccount> findByProjectId(String projectId);
}
