package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectJobConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectJob;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.ProjectJobRepository;
import com.fxlabs.fxt.dao.repository.UsersRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectJobServiceImpl extends GenericServiceImpl<ProjectJob, com.fxlabs.fxt.dto.project.ProjectJob, String> implements ProjectJobService {

    @Autowired
    public ProjectJobServiceImpl(ProjectJobRepository repository, ProjectJobConverter converter) {
        super(repository, converter);
    }

}
