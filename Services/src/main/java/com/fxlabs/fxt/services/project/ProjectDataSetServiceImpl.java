package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectDataSetConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectDataSet;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.ProjectDataSetRepository;
import com.fxlabs.fxt.dao.repository.UsersRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectDataSetServiceImpl extends GenericServiceImpl<ProjectDataSet, com.fxlabs.fxt.dto.project.ProjectDataSet, String> implements ProjectDataSetService {

    @Autowired
    public ProjectDataSetServiceImpl(ProjectDataSetRepository repository, ProjectDataSetConverter converter) {
        super(repository, converter);
    }

}
