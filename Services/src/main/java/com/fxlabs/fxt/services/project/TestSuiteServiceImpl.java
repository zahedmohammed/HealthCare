package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectConverter;
import com.fxlabs.fxt.converters.project.TestSuiteConverter;
import com.fxlabs.fxt.converters.project.TestSuiteMinConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.repository.es.ProjectFileESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.base.TestSuitesDeletedDto;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectFile;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import com.fxlabs.fxt.services.processors.send.GaaSTaskRequestProcessor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Service
@Transactional
public class TestSuiteServiceImpl extends GenericServiceImpl<TestSuite, com.fxlabs.fxt.dto.project.TestSuite, String> implements TestSuiteService {

    private TestSuiteESRepository testSuiteESRepository;
    private ProjectFileService projectFileService;
    private ProjectFileESRepository projectFileESRepository;
    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private ProjectConverter projectConverter;
    private TestSuiteRepository repository;
    private TestSuiteConverter testSuiteConverter;
    private TestSuiteMinConverter testSuiteMinConverter;
    private GaaSTaskRequestProcessor gaaSTaskRequestProcessor;

    @Autowired
    public TestSuiteServiceImpl(TestSuiteRepository repository, TestSuiteConverter converter, TestSuiteESRepository testSuiteESRepository, ProjectConverter projectConverter,
                                ProjectFileService projectFileService, ProjectService projectService, ProjectRepository projectRepository, GaaSTaskRequestProcessor gaaSTaskRequestProcessor,
                                ProjectFileESRepository projectFileESRepository, TestSuiteConverter testSuiteConverter, TestSuiteMinConverter testSuiteMinConverter) {
        super(repository, converter);
        this.repository = repository;
        this.testSuiteESRepository = testSuiteESRepository;
        this.projectFileService = projectFileService;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.projectFileESRepository = projectFileESRepository;
        this.testSuiteConverter = testSuiteConverter;
        this.testSuiteMinConverter  = testSuiteMinConverter;
        this.gaaSTaskRequestProcessor = gaaSTaskRequestProcessor;
        this.projectConverter = projectConverter;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.TestSuite>> findAll(String user, Pageable pageable) {
        Page<TestSuite> page = testSuiteESRepository.findByPublishToMarketplace(Boolean.TRUE, pageable);
        return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.TestSuite>> search(String keyword, String user, Pageable pageable) {
        Page<TestSuite> page = testSuiteESRepository.findByPublishToMarketplaceAndNameStartsWithIgnoreCase(Boolean.TRUE, keyword, pageable);
        List<com.fxlabs.fxt.dto.project.TestSuite> testSuites = converter.convertToDtos(page.getContent());

        testSuites.forEach(testSuite -> {
            testSuiteConverter.copyArraysToText(testSuite);
        });

        return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(testSuites, page.getTotalElements(), page.getTotalPages());
    }
    @Override
    public Response<List<com.fxlabs.fxt.dto.project.TestSuite>> findByProjectId(String id, String user, Pageable pageable) {
        Page<TestSuite> page = testSuiteESRepository.findByProjectId(id,pageable);

        List<com.fxlabs.fxt.dto.project.TestSuite> testSuites = converter.convertToDtos(page.getContent());

        testSuites.forEach(testSuite -> {
            testSuiteConverter.copyArraysToText(testSuite);
        });

        return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(testSuites, page.getTotalElements(), page.getTotalPages());
    }
    @Override
    public Response<com.fxlabs.fxt.dto.project.TestSuite> create(com.fxlabs.fxt.dto.project.TestSuite testSuite, String user) {

//        String fileName = testSuite.getProps().get(Project.FILE_NAME);
//        if (StringUtils.contains(fileName, "-")){
//            throw new FxException(String.format("FileName [%s] should not contain hypen '-'.", fileName));
//        }

        Optional<TestSuite> testSuiteOptional = ((TestSuiteRepository) repository).findByProjectIdAndName(testSuite.getProject().getId(), testSuite.getName());

        TestSuite entity = null;
        if (testSuiteOptional.isPresent()) {
            entity = testSuiteOptional.get();
            testSuite.setId(entity.getId());
        }

      //  testSuiteConverter.copyTextToArray(testSuite);

        // type
        if (testSuite.getType() == null) {
            testSuite.setType(TestSuiteType.SUITE);
        }

        TestSuite ts = converter.convertToEntity(testSuite);

        if (!testSuiteOptional.isPresent()) {
            Optional<com.fxlabs.fxt.dao.entity.project.Project> project = projectRepository.findById(testSuite.getProject().getId());
            ts.setProject(project.get());
        }

        entity = ((TestSuiteRepository) repository).save(ts);
        if (entity != null && entity.getId() != null) {
            testSuiteESRepository.save(entity);
        }

        // project_file
        this.projectFileService.saveFromTestSuite(testSuite, ts.getProject().getId());

        return new Response<com.fxlabs.fxt.dto.project.TestSuite>(converter.convertToDto(entity));

    }


    @Override
    public Response<com.fxlabs.fxt.dto.project.TestSuite> createFromUI(String yaml, String projectId, String user) {

        if (StringUtils.isEmpty(projectId)) {
            throw new FxException("Invalid Project");
        }

        com.fxlabs.fxt.dto.project.TestSuite dto = null;

        TestSuiteMin testSuiteMin = null;

        try {
            dto = testSuiteConverter.copyYamlToTestSuite(yaml);
            testSuiteMin = testSuiteMinConverter.copyYamlToTestSuiteMin(yaml);
        } catch (Exception ex) {
            throw new FxException(ex.getLocalizedMessage());
        }

        if (testSuiteMin != null && StringUtils.isNotEmpty(testSuiteMin.getParent())
                && org.apache.commons.lang3.StringUtils.containsIgnoreCase(testSuiteMin.getParent(), "AutoCode")) {
            throw new FxException("Folder name should not have AutoCode");
        }

        if (dto == null || StringUtils.isEmpty(dto.getName())) {
            throw new FxException("Invalid Test-Suite Name");
        }


        Optional<TestSuite> testSuiteOptional = ((TestSuiteRepository) repository).findByProjectIdAndName(projectId, dto.getName());


        if (testSuiteOptional.isPresent()) {
            throw new FxException(String.format("TestSuite [%s] exists.", testSuiteOptional.get().getName()));
        }

        testSuiteConverter.copyTextToArray(dto);

        // type
        if (dto.getType() == null) {
            dto.setType(TestSuiteType.SUITE);
        }


        TestSuite ts = converter.convertToEntity(dto);

        Optional<com.fxlabs.fxt.dao.entity.project.Project> project = projectRepository.findById(projectId);

//        ts.setProject(project.get());
//
//         TestSuite entity = ((TestSuiteRepository) repository).save(ts);
//        if (entity != null && entity.getId() != null) {
//            testSuiteESRepository.save(entity);
//        }
//
//        // project_file
//        this.projectFileService.saveFromTestSuite(dto, ts.getProject().getId());

        // Create GaaS Task
        this.gaaSTaskRequestProcessor.processAutoCodeconfig(project.get(), null, testSuiteMin);

        return new Response<com.fxlabs.fxt.dto.project.TestSuite>(dto);

    }



    @Override
    public Response<com.fxlabs.fxt.dto.project.TestSuite> update(com.fxlabs.fxt.dto.project.TestSuite testSuite, String user) {


        if (testSuite == null || testSuite.getProject() == null  || testSuite.getProject().getId() == null) {
            throw new FxException("Invalid Project");
        }


        TestSuiteMin testSuiteMin = null;
        com.fxlabs.fxt.dto.project.TestSuite testSuite_ = null;
        try {
            testSuite_ = testSuiteConverter.copyYamlToTestSuite(testSuite.getYaml());
            testSuiteMin = testSuiteMinConverter.copyYamlToTestSuiteMin(testSuite.getYaml());
        } catch (Exception ex) {
            throw new FxException(ex.getLocalizedMessage());
        }

        if (testSuiteMin != null && StringUtils.isNotEmpty(testSuiteMin.getParent())
                && org.apache.commons.lang3.StringUtils.containsIgnoreCase(testSuiteMin.getParent(), "AutoCode")) {
            throw new FxException("Folder name should not have AutoCode");
        }


        Optional<TestSuite> testSuiteOptional = ((TestSuiteRepository) repository).findByProjectIdAndName(testSuite.getProject().getId(), testSuite.getName());

        if (testSuiteOptional.isPresent() && !StringUtils.equals(testSuiteOptional.get().getId(), testSuite.getId())) {
            throw new FxException(String.format("TestSuite [%s] exists.", testSuiteOptional.get().getName()));
        }

        TestSuite entity = null;
        testSuite_.setId(testSuite.getId());

       // testSuiteConverter.copyTextToArray(testSuite);

        // type
        if (testSuite_.getType() == null) {
            testSuite_.setType(TestSuiteType.SUITE);
        }

        Optional<com.fxlabs.fxt.dao.entity.project.Project> project = projectRepository.findById(testSuite.getProject().getId());

        if (!project.isPresent()) {
              throw new FxException("Invalid Project");
          }
//        TestSuite ts = converter.convertToEntity(testSuite_);
//
//        if (testSuite.getProject() != null) {
//            Optional<com.fxlabs.fxt.dao.entity.project.Project> project = projectRepository.findById(testSuite.getProject().getId());
//            ts.setProject(project.get());
//        }
//
//       // entity = ((TestSuiteRepository) repository).save(ts);
//        if (entity != null && entity.getId() != null) {
//            testSuiteESRepository.save(ts);
//        }
//
//        // project_file
//        this.projectFileService.saveFromTestSuite(converter.convertToDto(ts), ts.getProject().getId());

        this.gaaSTaskRequestProcessor.processAutoCodeconfig(project.get(), null, testSuiteMin);

        return new Response<com.fxlabs.fxt.dto.project.TestSuite>(testSuite);

    }



    @Override
    public Response<com.fxlabs.fxt.dto.project.TestSuite> findById(String id, String org) {
        Optional<TestSuite> testSuiteOptional = testSuiteESRepository.findById(id);

        if (!testSuiteOptional.isPresent()) {
            throw new FxException("Invalid request for test-suite");
        }
        com.fxlabs.fxt.dto.project.TestSuite dto = converter.convertToDto(testSuiteOptional.get());
        testSuiteConverter.copyArraysToText(dto);
        //
        String yaml = testSuiteMinConverter.copyTestSuiteToYaml(testSuiteMinConverter.convertToEntity(dto));

        dto.setYaml(yaml);

        return new Response<>(dto);
    }


    @Override
    public void testSuitesDelete(TestSuitesDeletedDto dto, String user) {

        if (dto == null || StringUtils.isEmpty(dto.getProjectId())) {
            throw new FxException("Invalid request for file's synchronization");
        }

        dto.getDeletedFileNames().stream().forEach(df -> {
            try {
                Optional<TestSuite> testSuiteOptional = ((TestSuiteRepository) repository).findByProjectIdAndName(dto.getProjectId(), FilenameUtils.removeExtension(df));

                TestSuite entity = null;
                if (testSuiteOptional.isPresent()) {
                    entity = testSuiteOptional.get();
                }
                logger.info("Deleting file [{}] from ProjectId [{}]", df, dto.getProjectId());
                if (entity != null) {
                    repository.delete(entity);
                    testSuiteESRepository.delete(entity);
                    Optional<com.fxlabs.fxt.dao.entity.project.ProjectFile> projectFileResponse = this.projectFileESRepository.findByProjectIdAndFilenameIgnoreCase(dto.getProjectId(), df);

                    if (projectFileResponse.isPresent()) {
                        this.projectFileService.delete(projectFileResponse.get().getId(), projectFileResponse.get().getCreatedBy());
                        projectFileESRepository.delete(projectFileResponse.get());
                    }

                }
            }catch (Exception e){
                logger.info("Failed to delete file [{}] from ProjectId [{}]", df, dto.getProjectId());
                logger.warn(e.getLocalizedMessage());
            }

        });
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.TestSuite>> search(String projectId, String category, String keyword, String org, String user, Pageable pageable) {
        // TODO check org is entitled to runId

        // 1. filter by category and search string
        // 2. filter by category
        // 3. filter by search
        // 4. filter by severity and search string
        // 5. filter by status(pass/fail) and search string


        if (StringUtils.isNotEmpty(category) && StringUtils.isNotEmpty(keyword)) {

            Page<TestSuite> page = this.testSuiteESRepository.findByProjectIdAndCategoryAndNameContainingIgnoreCase(projectId, category, keyword, pageable);

            // filter by

            List<com.fxlabs.fxt.dto.project.TestSuite> testSuites = converter.convertToDtos(page.getContent());

            testSuites.forEach(testSuite -> {
                testSuiteConverter.copyArraysToText(testSuite);
            });

            return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(testSuites, page.getTotalElements(), page.getTotalPages());

        }


        if (StringUtils.isNotEmpty(keyword)) {

            Page<TestSuite> page = this.testSuiteESRepository.findByProjectIdAndNameContainingIgnoreCase(projectId, keyword, pageable);

            // filter by

            List<com.fxlabs.fxt.dto.project.TestSuite> testSuites = converter.convertToDtos(page.getContent());

            testSuites.forEach(testSuite -> {
                testSuiteConverter.copyArraysToText(testSuite);
            });

            return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(testSuites, page.getTotalElements(), page.getTotalPages());

        }

        if (StringUtils.isNotEmpty(category)) {

            Page<TestSuite> page = this.testSuiteESRepository.findByProjectIdAndCategory(projectId, category, pageable);

            // filter by

            List<com.fxlabs.fxt.dto.project.TestSuite> testSuites = converter.convertToDtos(page.getContent());

            testSuites.forEach(testSuite -> {
                testSuiteConverter.copyArraysToText(testSuite);
            });

            return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(testSuites, page.getTotalElements(), page.getTotalPages());

        } else {
            return findByProjectId(projectId, user, pageable);
        }

        //return new Response<>();
    }



    @Override
    public Response<Long> count(String user, Pageable pageable) {
        // check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner
        Response<List<Project>> projectsResponse = projectService.findProjects(user, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = repository.countByProjectIdAndInactive(p.getId(), false);
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }

    @Override
    public void isUserEntitled(String id, String user) {
        Optional<TestSuite> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
        projectService.isUserEntitled(optional.get().getProject().getId(), user);
    }
}
