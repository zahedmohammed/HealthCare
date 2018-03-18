package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectConverter;
import com.fxlabs.fxt.dao.entity.users.*;
import com.fxlabs.fxt.dao.repository.es.ProjectImportsESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectImports;
import com.fxlabs.fxt.dto.project.ProjectRequest;
import com.fxlabs.fxt.dto.project.ProjectType;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import com.fxlabs.fxt.services.processors.send.GaaSTaskRequestProcessor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class ProjectServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.project.Project, com.fxlabs.fxt.dto.project.Project, String> implements ProjectService {

    private ProjectFileService projectFileService;
    private ProjectRepository projectRepository;
    private ProjectGitAccountRepository projectGitAccountRepository;
    private OrgUsersRepository orgUsersRepository;
    //private TextEncryptor encryptor;
    private OrgRepository orgRepository;
    private UsersRepository usersRepository;
    private ProjectUsersRepository projectUsersRepository;
    private GaaSTaskRequestProcessor gaaSTaskRequestProcessor;
    private ProjectImportsRepository projectImportsRepository;
    private ProjectImportsESRepository projectImportsESRepository;

    private final static String PASSWORD_MASKED = "PASSWORD-MASKED";

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, ProjectConverter converter, ProjectFileService projectFileService,
                              ProjectGitAccountRepository projectGitAccountRepository, OrgUsersRepository orgUsersRepository,
                              /*TextEncryptor encryptor,*/ OrgRepository orgRepository, UsersRepository usersRepository,
                              ProjectUsersRepository projectUsersRepository, GaaSTaskRequestProcessor gaaSTaskRequestProcessor,
                              ProjectImportsRepository projectImportsRepository, ProjectImportsESRepository projectImportsESRepository) {
        super(repository, converter);
        this.projectRepository = repository;
        this.projectFileService = projectFileService;
        this.projectGitAccountRepository = projectGitAccountRepository;
        this.orgUsersRepository = orgUsersRepository;
        //this.encryptor = encryptor;
        this.orgRepository = orgRepository;
        this.usersRepository = usersRepository;
        this.projectUsersRepository = projectUsersRepository;
        this.gaaSTaskRequestProcessor = gaaSTaskRequestProcessor;
        this.projectImportsRepository = projectImportsRepository;
        this.projectImportsESRepository = projectImportsESRepository;
    }

    public Response<Project> findByName(String name, String owner) {
        Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = ((ProjectRepository) repository).findByNameAndInactive(name, false);

        if (projectOptional.isPresent()) {
            isUserEntitled(projectOptional.get().getId(), owner);
            return new Response<Project>(converter.convertToDto(projectOptional.get()));
        }
        return new Response<Project>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No Project found with the name [%s]", name)));
    }

    @Override
    public Response<Project> delete(String id, String user) {
        Response<Project> projectResponse = findById(id, user);
        if (projectResponse.isErrors()) {
            return projectResponse;
        }
        Project project = projectResponse.getData();
        project.setInactive(true);

        List<com.fxlabs.fxt.dao.entity.users.ProjectUsers> projectUsers = projectUsersRepository.findByProjectId(id);
        if (!CollectionUtils.isEmpty(projectUsers)) {
            projectUsers.forEach(pu -> {
                pu.setInactive(true);
                projectUsersRepository.save(pu);
            });
        }

        // TODO - Delete Jobs
        return save(project, user);
    }

    @Override
    public Response<Project> save(Project dto, String user) {

        // TODO - check user entitled to org

        Response<Project> projectResponse = super.save(dto, user);
        // set org

        // create project_file
        this.projectFileService.saveFromProject(dto, projectResponse.getData().getId());

        return projectResponse;
    }

    @Override
    public Response<List<Project>> findProjects(String owner, Pageable pageable) {
        List<com.fxlabs.fxt.dao.entity.users.ProjectUsers> projectUsers = projectUsersRepository.findByUsersIdAndRoleAndInactive(owner, ProjectRole.OWNER, false);
        if (CollectionUtils.isEmpty(projectUsers)) {
            return new Response<>();
        }
        final List<com.fxlabs.fxt.dao.entity.project.Project> projects = new ArrayList<>();
        projectUsers.stream().forEach(pu -> {
            if (BooleanUtils.isFalse(pu.getProject().isInactive()))
                projects.add(pu.getProject());
        });
        return new Response<List<Project>>(converter.convertToDtos(projects));
    }

    public Response<Long> countProjects(String owner) {
        Long count = projectUsersRepository.countByUsersIdAndInactive(owner, false);
        return new Response<>(count);
    }


    @Override
    public Response<Project> add(ProjectRequest request, String owner) {
        Response<Project> projectResponse = null;

        try {

            if (StringUtils.isEmpty(request.getName())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project name"));
            }

            OrgUsers orgUsers = null;
            if (request.getOrg() == null || StringUtils.isEmpty(request.getOrg().getId())) {
                /*Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(owner, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
                if (CollectionUtils.isEmpty(set)) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
                }

                orgUsers = set.iterator().next();
                request.setOrgId(orgUsers.getOrg().getId());*/
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid org"));

            } else {
                // check user had write access to Org
                Optional<com.fxlabs.fxt.dao.entity.users.OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndStatus(request.getOrg().getId(), owner, OrgUserStatus.ACTIVE);
                if (!orgUsersOptional.isPresent() || orgUsersOptional.get().getOrgRole() == OrgRole.READ) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [WRITE] or [ADMIN] access to the Org [%s]", request.getOrg().getId())));
                }
            }

            // check name is not duplicate
            Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = this.projectRepository.findByNameIgnoreCaseAndOrgIdAndInactive(request.getName(), request.getOrg().getId(), false);
            if (projectOptional.isPresent()) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Project with name [%s] exists", request.getName())));
            }

            // Validate GIT URL
            if (request.getProjectType() == ProjectType.GIT && StringUtils.isEmpty(request.getUrl())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "Project's GIT URL cannot be empty"));
            }

            // create project, project-git-account
            Project project = new Project();
            NameDto nameDto = new NameDto();
            Optional<com.fxlabs.fxt.dao.entity.users.Org> org = orgRepository.findById(request.getOrg().getId());

            nameDto.setId(org.get().getId());

            //nameDto.setVersion(org.get().getVersion());
            project.setOrg(nameDto);
            project.setName(request.getName());
            project.setDescription(request.getDescription());
            if (request.getProjectType() == null) {
                project.setProjectType(ProjectType.LOCAL);
            } else {
                project.setProjectType(request.getProjectType());
            }

            project.setVisibility(request.getVisibility());

            projectResponse = save(project, owner);
            if (projectResponse.isErrors()) {
                return projectResponse;
            }

            ProjectUsers projectUsers = new ProjectUsers();
            projectUsers.setProject(converter.convertToEntity(projectResponse.getData()));
            Optional<Users> users = usersRepository.findById(owner);
            projectUsers.setUsers(users.get());
            projectUsers.setRole(ProjectRole.OWNER);
            // save to db/es
            this.projectUsersRepository.saveAndFlush(projectUsers);


            // Git Account
            if (request.getProjectType() == ProjectType.GIT || true) {
                com.fxlabs.fxt.dao.entity.project.ProjectGitAccount account = new com.fxlabs.fxt.dao.entity.project.ProjectGitAccount();
                account.setUrl(request.getUrl());
                account.setBranch(request.getBranch());
                account.setUsername(request.getUsername());
                if (!StringUtils.isEmpty(request.getPassword())) {
                    // TODO - Use encryption
                    account.setPassword(request.getPassword());
                }
                account.setProjectId(projectResponse.getData().getId());
                this.projectGitAccountRepository.saveAndFlush(account);

                // Create GaaS Task
                this.gaaSTaskRequestProcessor.process(converter.convertToEntity(projectResponse.getData()));
            }


        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            projectResponse = new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", ex.getLocalizedMessage()));
        }
        return projectResponse;

    }

    @Override
    public Response<ProjectRequest> findGitByProjectId(String projectId, String user) {
        Response<Project> projectResponse = findById(projectId, user);
        if (projectResponse.isErrors()) {
            return new Response<>().withErrors(true).withMessages(projectResponse.getMessages());
        }

        Optional<com.fxlabs.fxt.dao.entity.project.ProjectGitAccount> accountOptional = projectGitAccountRepository.findByProjectId(projectId);
        if (!accountOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "No account found."));
        }
        Project _project = findById(projectId, user).getData();


        ProjectRequest project = new ProjectRequest();
        project.setName(projectResponse.getData().getName());
        project.setProjectType(projectResponse.getData().getProjectType());
        project.setId(accountOptional.get().getId());
        project.setProjectId(accountOptional.get().getProjectId());
        project.setUrl(accountOptional.get().getUrl());
        project.setBranch(accountOptional.get().getBranch());
        project.setUsername(accountOptional.get().getUsername());
        project.setPassword(PASSWORD_MASKED);
        project.setOrg(projectResponse.getData().getOrg());
        project.setVisibility(_project.getVisibility());

        return new Response<ProjectRequest>(project);
    }

    @Override
    public Response<ProjectRequest> saveGitAccount(ProjectRequest request, String user) {
        Response<Project> projectResponse = findById(request.getProjectId(), user);
        if (projectResponse.isErrors()) {
            return new Response<>().withErrors(true).withMessages(projectResponse.getMessages());
        }

        Optional<com.fxlabs.fxt.dao.entity.project.ProjectGitAccount> accountOptional = projectGitAccountRepository.findByProjectId(request.getProjectId());

        com.fxlabs.fxt.dao.entity.project.ProjectGitAccount account = null;

        if (accountOptional.isPresent()) {
            account = accountOptional.get();
        } else {
            account = new com.fxlabs.fxt.dao.entity.project.ProjectGitAccount();
            account.setProjectId(request.getProjectId());
        }

        account.setUrl(request.getUrl());
        account.setBranch(request.getBranch());
        account.setUsername(request.getUsername());

        if (!org.apache.commons.lang3.StringUtils.equals(PASSWORD_MASKED, request.getPassword())) {
            account.setPassword(request.getPassword());
        }

        this.projectGitAccountRepository.saveAndFlush(account);

        Project project = findById(request.getProjectId(), user).getData();
        if (project.getVisibility() != request.getVisibility()) {
            project.setVisibility(request.getVisibility());
            save(project, user);
        }

        // Create GaaS Task
        this.gaaSTaskRequestProcessor.process(converter.convertToEntity(projectResponse.getData()));

        return new Response<ProjectRequest>();
    }

    @Override
    public Response<Boolean> saveProjectImports(ProjectImports projectImports, String user) {

        try {
            Response<Project> projectResponse = findById(projectImports.getProjectId(), user);
            if (projectResponse.isErrors()) {
                return new Response<>().withErrors(true).withMessages(projectResponse.getMessages());
            }

            com.fxlabs.fxt.dao.entity.project.ProjectImports imports = null;
            Optional<com.fxlabs.fxt.dao.entity.project.ProjectImports> optional = this.projectImportsRepository.findByProjectId(projectImports.getProjectId());
            if (optional.isPresent()) {
                imports = optional.get();
            } else {
                imports = new com.fxlabs.fxt.dao.entity.project.ProjectImports();
            }
            imports.setProjectId(projectImports.getProjectId());
            imports.setImports(projectImports.getImports());
            imports = projectImportsRepository.save(imports);
            projectImportsESRepository.save(imports);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", ex.getLocalizedMessage()));
        }

        return new Response<>(true);

    }

    public void isUserEntitled(String id, String user) {
        Optional<com.fxlabs.fxt.dao.entity.users.ProjectUsers> projectUsersOptional = projectUsersRepository.findByProjectIdAndUsersIdAndRole(id, user, ProjectRole.OWNER);

        if (!projectUsersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }

    }
}
