package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.AutoCodeConfigConverter;
import com.fxlabs.fxt.converters.project.AutoCodeGeneratorConverter;
import com.fxlabs.fxt.converters.project.ProjectConverter;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.entity.users.Org;
import com.fxlabs.fxt.dao.repository.es.ProjectImportsESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.clusters.AccountService;
import com.fxlabs.fxt.services.processors.send.GaaSTaskRequestProcessor;
import com.fxlabs.fxt.services.users.SystemSettingService;
import com.fxlabs.fxt.services.util.AutoCodeConfigServiceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Luqman Shareef
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
    private SystemSettingService systemSettingService;
    private JobRepository jobRepository;
    private AutoCodeConfigRepository autoCodeConfigRepository;
    private AutoCodeConfigConverter autoCodeConfigConverter;
    private AutoCodeGeneratorRepository autoCodeGeneratorRepository;
    private AutoCodeGeneratorConverter autoCodeGeneratorConverter;


    private final static String PASSWORD_MASKED = "PASSWORD-MASKED";

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, ProjectConverter converter, ProjectFileService projectFileService,
                              OrgUsersRepository orgUsersRepository, JobRepository jobRepository, AutoCodeConfigConverter autoCodeConfigConverter,
                              OrgRepository orgRepository, UsersRepository usersRepository,
                              GaaSTaskRequestProcessor gaaSTaskRequestProcessor, AutoCodeConfigRepository autoCodeConfigRepository,
                              ProjectImportsRepository projectImportsRepository, ProjectImportsESRepository projectImportsESRepository,
                              AccountService accountService, SystemSettingService systemSettingService, AutoCodeGeneratorRepository autoCodeGeneratorRepository, AutoCodeGeneratorConverter autoCodeGeneratorConverter) {

        super(repository, converter);
        this.autoCodeGeneratorConverter = autoCodeGeneratorConverter;
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
        this.systemSettingService = systemSettingService;
        this.jobRepository = jobRepository;
        this.autoCodeConfigRepository = autoCodeConfigRepository;
        this.autoCodeConfigConverter = autoCodeConfigConverter;
        this.autoCodeGeneratorRepository = autoCodeGeneratorRepository;
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

        Optional<com.fxlabs.fxt.dao.entity.project.Project> projectOptional = ((ProjectRepository) repository).findByOrgNameAndNameAndInactive(o, name, false);

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
        List<Job> responseJobs = jobRepository.findByProjectIdAndInactive(id, false, PageRequest.of(0, 20, new Sort(Sort.Direction.DESC, "createdDate")));
        for (Job job : responseJobs) {
            job.setInactive(true);
        }

        jobRepository.saveAll(responseJobs);
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
//            if (request.getGenPolicy() == null) {
//                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Auto-Code option should selected."));
//            }
//
//            // check OpenAPISpec
//            if (request.getGenPolicy() == GenPolicy.Create && StringUtils.isEmpty(request.getOpenAPISpec())) {
//                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "OpenAPISpec is required."));
//            }

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

            Org org_ = orgRepository.findById(org).get();
            NameDto o = new NameDto();
            o.setId(org_.getId());
            o.setName(org_.getName());

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
                this.gaaSTaskRequestProcessor.process(converter.convertToEntity(projectResponse.getData()), null);
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
            //return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Auto-Code option should selected."));
        }

        // check OpenAPISpec
        if (request.getGenPolicy() == GenPolicy.Create && StringUtils.isEmpty(request.getOpenAPISpec())) {
            //return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "OpenAPISpec is required."));
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
        //  this.gaaSTaskRequestProcessor.process(converter.convertToEntity(project), null);

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

    @Override
    public Response<ProjectSaving> getProjectSavings(String id, String org, String owner) {
        ProjectSaving saving = new ProjectSaving();

        Response<Project> projResp = this.findById(id, org);

        Project project = projResp.getData();

        if (project == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Project Not found"));
        }

        saving.setId(project.getId());
        saving.setName(project.getName());

        int generatedSuites = 0;
        try {
            generatedSuites = project.getAutoGenSuites();
        } catch (Exception ex) {
        }

        // Default values, in case not found in DB
        int suiteCodeHrs = 8;
        int suiteCodeCost = 50;

        String suiteCodeHrsStr = systemSettingService.findByKey("SUITE_CODE_HRS");
        String suiteCodeCostStr = systemSettingService.findByKey("SUITE_CODE_COST_PER_HR");

        try {
            suiteCodeHrs = Integer.parseInt(suiteCodeHrsStr);
            suiteCodeCost = Integer.parseInt(suiteCodeCostStr);
        } catch (Exception ex) {
            // Default values will be used
        }

        saving.setAutoGenAllSuites(generatedSuites);
        saving.setAutoGenSecuritySuites(generatedSuites);

        saving.setTotalTimeSaving((long) saving.getAutoGenAllSuites() * suiteCodeHrs);
        saving.setTotalCostSaving((long) saving.getAutoGenAllSuites() * suiteCodeHrs * suiteCodeCost);

        saving.setSecurityCoverageTimeSaving((long) saving.getAutoGenSecuritySuites() * suiteCodeHrs);
        saving.setSecurityCoverageCostSaving((long) saving.getAutoGenSecuritySuites() * suiteCodeHrs * suiteCodeCost);

        return new Response(saving);
    }

    @Override
    public Response<Boolean> saveProjectSync(ProjectSync request, String org) {

        if (request == null || StringUtils.isEmpty(request.getProjectId())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for Sync Project "));
        }

        Optional<com.fxlabs.fxt.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(request.getProjectId(), org);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        Project project = converter.convertToDto(optionalProject.get());
//        // check OpenAPISpec
//        if (project.getGenPolicy() != GenPolicy.Create || StringUtils.isEmpty(project.getOpenAPISpec())) {
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "AutoCode option should be set to Create."));
//        }


        // check account access
        Response<Account> accountResponse = accountService.findById(project.getAccount().getId(), org);
        if (accountResponse == null || accountResponse.isErrors()) {
            return new Response<>().withErrors(true).withMessages(accountResponse.getMessages());
        }

        // Create GaaS Task
        this.gaaSTaskRequestProcessor.process(converter.convertToEntity(project), request);

        return new Response<Boolean>(true);
    }

    @Override
    public Response<AutoCodeConfig> saveAutoCode(String projectId, AutoCodeConfig codeConfig, String orgId) {

        if (codeConfig == null || StringUtils.isEmpty(projectId)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for  Project Autocode configuaration"));
        }

        if (codeConfig.getGenPolicy() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Auto-Code option should be selected."));
        }

        // check OpenAPISpec
        if (codeConfig.getGenPolicy() == GenPolicy.Create && StringUtils.isEmpty(codeConfig.getOpenAPISpec())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "OpenAPISpec is required."));
        }

        Optional<com.fxlabs.fxt.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(projectId, orgId);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        Project project = converter.convertToDto(optionalProject.get());
//        // check OpenAPISpec
//        if (project.getGenPolicy() != GenPolicy.Create || StringUtils.isEmpty(project.getOpenAPISpec())) {
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "AutoCode option should be set to Create."));
//        }


        // check account access
        Response<Account> accountResponse = accountService.findById(project.getAccount().getId(), orgId);
        if (accountResponse == null || accountResponse.isErrors()) {
            return new Response<>().withErrors(true).withMessages(accountResponse.getMessages());
        }
        codeConfig.setProject(project);


//        project.setGenPolicy(codeConfig.getGenPolicy());
//        if (codeConfig.getGenPolicy() == GenPolicy.Create) {
//            project.setOpenAPISpec(codeConfig.getOpenAPISpec());
//        } else {
//            project.setOpenAPISpec(null);
//        }
//        Response<Project> projectResponse = save(project);
//        if (projectResponse.isErrors()) {
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Autocode config failed"));
//        }

        autoCodeConfigConverter.copyAssertionsTextToArray(codeConfig);

        List<com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeGenerator> generators = autoCodeGeneratorRepository.saveAll(autoCodeGeneratorConverter.convertToEntities(codeConfig.getGenerators()));

        com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig entity = autoCodeConfigConverter.convertToEntity(codeConfig);

        entity.setGenerators(generators);
        entity = autoCodeConfigRepository.save(entity);

        // Create GaaS Task
        this.gaaSTaskRequestProcessor.processAutoCodeconfig(converter.convertToEntity(project), codeConfig);
        AutoCodeConfig autoCodeConfig = autoCodeConfigConverter.convertToDto(entity);
        autoCodeConfigConverter.copyAssertionsToText(autoCodeConfig);
        return new Response<>(autoCodeConfig);
    }

    @Override
    public Response<AutoCodeConfig> getAutoCodeById(String projectId, String orgId) {

        Optional<com.fxlabs.fxt.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(projectId, orgId);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        Response<AutoCodeConfig> response = null;
        Optional<com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig> entityOptional = autoCodeConfigRepository.findByProjectId(projectId);

        if (entityOptional.isPresent()) {
            response = new Response<>(autoCodeConfigConverter.convertToDto(entityOptional.get()));
        }

        if (response == null) {
            response = new Response<>(AutoCodeConfigServiceUtil.getAutoCodeConfig(converter.convertToDto(optionalProject.get())));
        }

        autoCodeConfigConverter.copyAssertionsToText(response.getData());

        return response;
    }


}
