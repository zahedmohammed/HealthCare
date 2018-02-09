package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.users.ProjectUsersConverter;
import com.fxlabs.fxt.dao.repository.es.ProjectUsersESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class ProjectUsersServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.users.ProjectUsers, com.fxlabs.fxt.dto.users.ProjectUsers, String> implements ProjectUsersService {

    private ProjectUsersRepository projectUsersRepository;
    private ProjectUsersESRepository projectUsersESRepository;
    private ProjectUsersConverter projectUsersConverter;

    @Autowired
    public ProjectUsersServiceImpl(ProjectUsersRepository projectUsersRepository, ProjectUsersESRepository projectUsersESRepository,
                                   ProjectUsersConverter projectUsersConverter) {
        super(projectUsersRepository, projectUsersConverter);

        this.projectUsersRepository = projectUsersRepository;
        this.projectUsersESRepository = projectUsersESRepository;
        this.projectUsersConverter = projectUsersConverter;

    }


    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }
}
