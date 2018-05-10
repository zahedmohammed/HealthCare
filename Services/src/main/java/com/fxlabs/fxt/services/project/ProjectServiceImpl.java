package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectConverter;
import com.fxlabs.fxt.dao.entity.users.*;
import com.fxlabs.fxt.dao.repository.es.ProjectImportsESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.GenPolicy;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectImports;
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

    
    @Override
    public Response<Project> findByName(String name, String owner) {
        Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = ((ProjectRepository) repository).findByNameAndInactive(name, false);

        if (projectOptional.isPresent()) {
            isUserEntitled(projectOptional.get().getId(), owner);
            return new Response<Project>(converter.convertToDto(projectOptional.get()));
        }
        return new Response<Project>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No Project found with the name [%s]", name)));
    }

    @Override
    public Response<Project> findByOrgAndName(String name, String owner) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project name"));
        }
        String[] tokens = name.split("/");
        if (tokens.length != 2) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, String.format("Invalid project name [%s]. Valid e.g. 'org/project'", name)));
        }
        String org = tokens[0];
        String proj = tokens[1];

        Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = ((ProjectRepository) repository).findByOrgNameAndNameAndInactive(org, proj, false);

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
        return new Response<List<Project>>(converter.convertToDtos(projects), new Long(projects.size()), projects.size());
    }

    public Response<Long> countProjects(String owner) {
        Long count = projectUsersRepository.countByUsersIdAndInactive(owner, false);
        return new Response<>(count);
    }


    @Override
    public Response<Project> add(Project request, String owner) {
        Response<Project> projectResponse = null;

        try {

            // check name
            if (StringUtils.isEmpty(request.getName())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project name"));
            }

            // check org
            OrgUsers orgUsers = null;
            if (request.getOrg() == null || StringUtils.isEmpty(request.getOrg().getId())) {
                /*Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(owner, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
                if (CollectionUtils.isEmpty(set)) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
                }

                orgUsers = set.iterator().next();
                request.setOrgId(orgUsers.getOrg().getId());*/
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid org"));

            }

            // check account
            if (request.getCloudAccount() == null) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid account"));
            }

            // check auto-code
            if (request.getGenPolicy() == null) {
                request.setGenPolicy(GenPolicy.None);
            }

            // check user had write access to Org
            Optional<com.fxlabs.fxt.dao.entity.users.OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndStatus(request.getOrg().getId(), owner, OrgUserStatus.ACTIVE);
            if (!orgUsersOptional.isPresent() || orgUsersOptional.get().getOrgRole() == OrgRole.READ) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [WRITE] or [ADMIN] access to the Org [%s]", request.getOrg().getId())));
            }

            // check name is not duplicate
            Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = this.projectRepository.findByNameIgnoreCaseAndOrgIdAndInactive(request.getName(), request.getOrg().getId(), false);
            if (projectOptional.isPresent()) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Project with name [%s] exists", request.getName())));
            }

            // Validate GIT URL
            if (request.getCloudAccount().getAccountType() != com.fxlabs.fxt.dto.clusters.AccountType.Local && StringUtils.isEmpty(request.getUrl())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "Project's GIT URL cannot be empty"));
            }

            // create project, project-git-account
            Project project = new Project();
            NameDto nameDto = new NameDto();
            Optional<com.fxlabs.fxt.dao.entity.users.Org> org = orgRepository.findById(request.getOrg().getId());

            nameDto.setId(org.get().getId());

            //nameDto.setVersion(org.get().getVersion());
            project.setCloudAccount(request.getCloudAccount());
            project.setOrg(nameDto);
            project.setName(request.getName());
            project.setDescription(request.getDescription());
            project.setUrl(request.getUrl());
            project.setBranch(request.getBranch());
            project.setGenPolicy(request.getGenPolicy());
            project.setOpenAPISpec(request.getOpenAPISpec());

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

            // Create GaaS Task
            if (request.getCloudAccount().getAccountType() != com.fxlabs.fxt.dto.clusters.AccountType.Local) {
                this.gaaSTaskRequestProcessor.process(converter.convertToEntity(projectResponse.getData()));
            }

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            projectResponse = new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", ex.getLocalizedMessage()));
        }
        return projectResponse;

    }

    @Override
    public Response<Project> findGitByProjectId(String projectId, String user) {
        Response<Project> projectResponse = findById(projectId, user);
        if (projectResponse.isErrors()) {
            return new Response<>().withErrors(true).withMessages(projectResponse.getMessages());
        }

        Project _project = projectResponse.getData();

        Project project = new Project();

        project.setOrg(_project.getOrg());

        project.setName(_project.getName());
        project.setProjectType(_project.getProjectType());

        project.setId(_project.getId());
        project.setUrl(_project.getUrl());
        project.setBranch(_project.getBranch());
        project.setCloudAccount(_project.getCloudAccount());
        project.setGenPolicy(_project.getGenPolicy());
        project.setOpenAPISpec(_project.getOpenAPISpec());

        project.setVisibility(_project.getVisibility());

        return new Response<Project>(project);
    }

    @Override
    public Response<Project> saveGitAccount(Project request, String user) {
        Response<Project> projectResponse = findById(request.getId(), user);
        if (projectResponse.isErrors()) {
            return new Response<>().withErrors(true).withMessages(projectResponse.getMessages());
        }

        Project project = projectResponse.getData();

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setUrl(request.getUrl());
        project.setBranch(request.getBranch());
        project.setGenPolicy(request.getGenPolicy());
        project.setOpenAPISpec(request.getOpenAPISpec());

        project.setVisibility(request.getVisibility());

        this.save(project, user);

        // Create GaaS Task
        this.gaaSTaskRequestProcessor.process(converter.convertToEntity(projectResponse.getData()));

        return new Response<Project>();
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
