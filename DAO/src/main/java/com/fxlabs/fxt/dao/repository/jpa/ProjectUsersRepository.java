package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUsersRepository extends JpaRepository<ProjectUsers, String> {
}
