package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectEnvironmentConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectEnvironment;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.ProjectEnvironmentRepository;
import com.fxlabs.fxt.dao.repository.UsersRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectEnvironmentServiceImpl extends GenericServiceImpl<ProjectEnvironment, com.fxlabs.fxt.dto.project.ProjectEnvironment, String> implements ProjectEnvironmentService {

    @Autowired
    public ProjectEnvironmentServiceImpl(ProjectEnvironmentRepository repository, ProjectEnvironmentConverter converter) {
        super(repository, converter);
    }

}
