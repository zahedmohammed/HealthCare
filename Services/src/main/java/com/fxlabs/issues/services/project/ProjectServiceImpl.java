package com.fxlabs.issues.services.project;

import com.fxlabs.issues.converters.project.ProjectConverter;
import com.fxlabs.issues.dao.entity.project.Project;
import com.fxlabs.issues.dao.repository.jpa.OrgRepository;
import com.fxlabs.issues.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.issues.dao.repository.jpa.ProjectRepository;
import com.fxlabs.issues.dao.repository.jpa.UsersRepository;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.fxlabs.issues.services.users.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class ProjectServiceImpl extends GenericServiceImpl<Project, com.fxlabs.issues.dto.project.Project, String> implements ProjectService {

    private ProjectRepository projectRepository;
    private OrgUsersRepository orgUsersRepository;
    //private TextEncryptor encryptor;
    private OrgRepository orgRepository;
    private UsersRepository usersRepository;
    private SystemSettingService systemSettingService;


    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, ProjectConverter converter,
                              OrgUsersRepository orgUsersRepository,
                              OrgRepository orgRepository, UsersRepository usersRepository,
                              SystemSettingService systemSettingService) {

        super(repository, converter);

        this.projectRepository = repository;
        this.orgUsersRepository = orgUsersRepository;
        this.orgRepository = orgRepository;
        this.usersRepository = usersRepository;
        this.systemSettingService = systemSettingService;

    }


    @Override
    public void isUserEntitled(String s, String user) {

    }
}
