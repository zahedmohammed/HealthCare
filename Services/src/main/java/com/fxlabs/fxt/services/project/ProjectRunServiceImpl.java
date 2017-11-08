package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectRunConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectRun;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.ProjectRunRepository;
import com.fxlabs.fxt.dao.repository.UsersRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectRunServiceImpl extends GenericServiceImpl<ProjectRun, com.fxlabs.fxt.dto.project.ProjectRun, String> implements ProjectRunService {

    @Autowired
    public ProjectRunServiceImpl(ProjectRunRepository repository, ProjectRunConverter converter) {
        super(repository, converter);
    }

}
