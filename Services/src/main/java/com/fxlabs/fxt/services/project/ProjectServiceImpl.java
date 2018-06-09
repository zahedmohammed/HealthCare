package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectConverter;
import com.fxlabs.fxt.dao.repository.es.ProjectImportsESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.dto.project.GenPolicy;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectImports;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.clusters.AccountService;
import com.fxlabs.fxt.services.processors.send.GaaSTaskRequestProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private OrgUsersRepository orgUsersRepository;
    //private TextEncryptor encryptor;
    private OrgRepository orgRepository;
    private UsersRepository usersRepository;
    private GaaSTaskRequestProcessor gaaSTaskRequestProcessor;
    private ProjectImportsRepository projectImportsRepository;
    private ProjectImportsESRepository projectImportsESRepository;
    private AccountService accountService;

    private final static String PASSWORD_MASKED = "PASSWORD-MASKED";

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, ProjectConverter converter, ProjectFileService projectFileService,
                              OrgUsersRepository orgUsersRepository,
                              OrgRepository orgRepository, UsersRepository usersRepository,
                              GaaSTaskRequestProcessor gaaSTaskRequestProcessor,
                              ProjectImportsRepository projectImportsRepository, ProjectImportsESRepository projectImportsESRepository,
                              AccountService accountService) {

        super(repository, converter);

        this.projectRepository = repository;
        this.projectFileService = projectFileService;
        this.orgUsersRepository = orgUsersRepository;
        //this.encryptor = encryptor;
        this.orgRepository = orgRepository;
        this.usersRepository = usersRepository;
        this.gaaSTaskRequestProcessor = gaaSTaskRequestProcessor;
        this.projectImportsRepository = projectImportsRepository;
        this.projectImportsESRepository = projectImportsESRepository;
        this.accountService = accountService;
    }


    @Override
    public Response<Project> findByName(String name, String o) {
        Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = ((ProjectRepository) repository).findByNameAndInactive(name, false);

        if (projectOptional.isPresent()) {
            if (org.apache.commons.lang3.StringUtils.equals(projectOptional.get().getOrg().getId(), o))
                return new Response<Project>(converter.convertToDto(projectOptional.get()));
        }
        return new Response<Project>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No Project found with the name [%s]", name)));
    }

    @Override
    public Response<Project> findByOrgAndName(String name, String o) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project name"));
        }
        String[] tokens = name.split("/");
        if (tokens.length != 2) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, String.format("Invalid project name [%s]. Valid e.g. 'org/project'", name)));
        }
        String org_ = tokens[0];
        String proj = tokens[1];

        Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = ((ProjectRepository) repository).findByOrgNameAndNameAndInactive(org_, proj, false);

        if (projectOptional.isPresent() && org.apache.commons.lang3.StringUtils.equals(projectOptional.get().getOrg().getId(), o))
            return new Response<Project>(converter.convertToDto(projectOptional.get()));

        return new Response<Project>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No Project found with the name [%s]", name)));
    }

    @Override
    public Response<Project> delete(String id, String org, String user) {

        // check user entitled to org
        Optional<com.fxlabs.fxt.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(id, org);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        Project project = converter.convertToDto(optionalProject.get());
        project.setInactive(true);

        // TODO - Delete Jobs
        return save(project, user);
    }

    @Override
    public Response<Project> save(Project dto, String org, String user) {


        if (dto == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for Account"));
        }

        if (StringUtils.isEmpty(dto.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Name is empty"));
        }

        if (dto.getAccount() == null || StringUtils.isEmpty(dto.getAccount().getId())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Account is empty"));
        }


        // check user entitled to org
        Optional<com.fxlabs.fxt.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(dto.getId(), org);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        Response<Project> projectResponse = super.save(dto, user);
        // set org

        // create project_file
        this.projectFileService.saveFromProject(dto, projectResponse.getData().getId());

        return projectResponse;
    }

    @Override
    public Response<List<Project>> findProjects(String org, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.project.Project> page = projectRepository.findByOrgIdAndInactive(org, false, pageable);
        return new Response<List<Project>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<Project> findById(String id, String org) {
        Optional<com.fxlabs.fxt.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(id, org);
        return new Response(converter.convertToDto(optionalProject.get()));
    }

    public Response<Long> countProjects(String org) {
        Long count = projectRepository.countByOrgIdAndInactive(org, false);
        return new Response<>(count);
    }


    @Override
    public Response<Project> add(Project request, String org, String owner) {
        Response<Project> projectResponse = null;

        try {

            if (request == null) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for Project"));
            }

            // check name
            if (StringUtils.isEmpty(request.getName())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project name"));
            }

            if (StringUtils.isEmpty(request.getUrl())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project URL"));
            }

            // check account
            if (request.getAccount() == null || StringUtils.isEmpty(request.getAccount().getId())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid account"));
            }

            // check auto-code
            if (request.getGenPolicy() == null) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Auto-Code option should selected."));
            }

            // check OpenAPISpec
            if (request.getGenPolicy() == GenPolicy.Create && StringUtils.isEmpty(request.getOpenAPISpec())) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "OpenAPISpec is required."));
            }

            // check name is not duplicate
            Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = this.projectRepository.findByNameIgnoreCaseAndOrgIdAndInactive(request.getName(), org, false);
            if (projectOptional.isPresent()) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Project with name [%s] exists", request.getName())));
            }

            // check account access
            Response<Account> accountResponse = accountService.findById(request.getAccount().getId(), org);
            if (accountResponse == null || accountResponse.isErrors()) {
                return new Response<>().withErrors(true).withMessages(accountResponse.getMessages());
            }


            NameDto o = new NameDto();
            o.setId(org);
            request.setOrg(o);

            // create project, project-git-account
            Project project = new Project();

            project.setAccount(request.getAccount());
            project.setOrg(o);
            project.setName(request.getName());
            project.setDescription(request.getDescription());
            project.setUrl(request.getUrl());
            project.setBranch(request.getBranch());
            project.setGenPolicy(request.getGenPolicy());
            if (project.getGenPolicy() == GenPolicy.Create) {
                project.setOpenAPISpec(request.getOpenAPISpec());
            }

            projectResponse = save(project, owner);

            if (projectResponse.isErrors()) {
                return projectResponse;
            }

            // Create GaaS Task
            if (request.getAccount().getAccountType() != com.fxlabs.fxt.dto.clusters.AccountType.Local) {
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

        project.setId(_project.getId());
        project.setUrl(_project.getUrl());
        project.setBranch(_project.getBranch());
        project.setAccount(_project.getAccount());
        project.setGenPolicy(_project.getGenPolicy());
        project.setOpenAPISpec(_project.getOpenAPISpec());

        return new Response<Project>(project);
    }

    @Override
    public Response<Project> saveProject(Project request, String org, String user) {

        if (request == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for update Project "));
        }

        Optional<com.fxlabs.fxt.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(request.getId(), org);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        // check org
        if (!StringUtils.equals(request.getOrg().getId(), org)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid org"));
        }

        if (StringUtils.isEmpty(request.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project name"));
        }

        if (StringUtils.isEmpty(request.getUrl())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project URL"));
        }

        // check account
        if (request.getAccount() == null || StringUtils.isEmpty(request.getAccount().getId())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid account"));
        }

        // check auto-code
        if (request.getGenPolicy() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Auto-Code option should selected."));
        }

        // check OpenAPISpec
        if (request.getGenPolicy() == GenPolicy.Create && StringUtils.isEmpty(request.getOpenAPISpec())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "OpenAPISpec is required."));
        }

        // check name is not duplicate
        Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = this.projectRepository.findByNameIgnoreCaseAndIdNotLikeAndOrgIdAndInactive(request.getName(), request.getId(), request.getOrg().getId(), false);
        if (projectOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Project with name [%s] exists", request.getName())));
        }

        // check account access
        Response<Account> accountResponse = accountService.findById(request.getAccount().getId(), org);
        if (accountResponse == null || accountResponse.isErrors()) {
            return new Response<>().withErrors(true).withMessages(accountResponse.getMessages());
        }

        Project project = converter.convertToDto(optionalProject.get());

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setUrl(request.getUrl());
        project.setBranch(request.getBranch());
        project.setGenPolicy(request.getGenPolicy());
        if (project.getGenPolicy() == GenPolicy.Create) {
            project.setOpenAPISpec(request.getOpenAPISpec());
        } else {
            project.setOpenAPISpec(null);
        }
        project.setAccount(request.getAccount());

        this.save(project, user);

        // Create GaaS Task
        this.gaaSTaskRequestProcessor.process(converter.convertToEntity(project));

        return new Response<Project>();
    }

    @Override
    public Response<Boolean> saveProjectImports(ProjectImports projectImports, String org) {

        try {
            Response<Project> projectResponse = findById(projectImports.getProjectId(), org);
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
        /*Optional<com.fxlabs.fxt.dao.entity.users.ProjectUsers> projectUsersOptional = projectUsersRepository.findByProjectIdAndUsersIdAndRole(id, user, ProjectRole.OWNER);

        if (!projectUsersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }*/

    }
}
