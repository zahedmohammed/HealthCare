package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.TestSuiteConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class TestSuiteServiceImpl extends GenericServiceImpl<TestSuite, com.fxlabs.fxt.dto.project.TestSuite, String> implements TestSuiteService {

    private TestSuiteESRepository testSuiteESRepository;
    private ProjectFileService projectFileService;
    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private TestSuiteRepository repository;

    @Autowired
    public TestSuiteServiceImpl(TestSuiteRepository repository, TestSuiteConverter converter, TestSuiteESRepository testSuiteESRepository,
                                ProjectFileService projectFileService, ProjectService projectService, ProjectRepository projectRepository) {
        super(repository, converter);
        this.repository = repository;
        this.testSuiteESRepository = testSuiteESRepository;
        this.projectFileService = projectFileService;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.TestSuite>> findAll(String user, Pageable pageable) {
        Page<TestSuite> page = testSuiteESRepository.findByPublishToMarketplace(Boolean.TRUE, pageable);
        return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.TestSuite>> search(String keyword, String user, Pageable pageable) {
        Page<TestSuite> page = testSuiteESRepository.findByPublishToMarketplaceAndNameStartsWithIgnoreCase(Boolean.TRUE, keyword, pageable);
        return new Response<List<com.fxlabs.fxt.dto.project.TestSuite>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.TestSuite> save(com.fxlabs.fxt.dto.project.TestSuite testSuite, String user) {

        String fileName = testSuite.getProps().get(Project.FILE_NAME);
        if (StringUtils.contains(fileName, "-")){
            throw new FxException(String.format("FileName [%s] should not contain hypen '-'.", fileName));
        }
        Optional<TestSuite> testSuiteOptional = ((TestSuiteRepository) repository).findByProjectIdAndName(testSuite.getProject().getId(), testSuite.getName());

        TestSuite entity = null;
        if (testSuiteOptional.isPresent()) {
            entity = testSuiteOptional.get();
            testSuite.setId(entity.getId());
        }

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
        testSuiteESRepository.save(entity);

        // project_file
        this.projectFileService.saveFromTestSuite(testSuite, ts.getProject().getId());

        return new Response<com.fxlabs.fxt.dto.project.TestSuite>(converter.convertToDto(entity));

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
