package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.users.ProjectRole;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectUsersRepository extends JpaRepository<ProjectUsers, String> {

    List<ProjectUsers> findByUsersIdAndRole(String owner, ProjectRole role);

    List<ProjectUsers> findByProjectIdAndRole(String projectId, ProjectRole role);
}
