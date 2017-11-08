package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectCredentialConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectCredential;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.ProjectCredentialRepository;
import com.fxlabs.fxt.dao.repository.UsersRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectCredentialServiceImpl extends GenericServiceImpl<ProjectCredential, com.fxlabs.fxt.dto.project.ProjectCredential, String> implements ProjectCredentialService {

    @Autowired
    public ProjectCredentialServiceImpl(ProjectCredentialRepository repository, ProjectCredentialConverter converter) {
        super(repository, converter);
    }

}
