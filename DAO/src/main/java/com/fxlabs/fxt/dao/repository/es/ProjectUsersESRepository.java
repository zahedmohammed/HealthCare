package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.ProjectFile;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectUsersESRepository extends ElasticsearchRepository<ProjectUsers, String> {

}
