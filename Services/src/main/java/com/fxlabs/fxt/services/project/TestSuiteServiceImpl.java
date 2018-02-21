package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.TestSuiteConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class TestSuiteServiceImpl extends GenericServiceImpl<TestSuite, com.fxlabs.fxt.dto.project.TestSuite, String> implements TestSuiteService {

    private TestSuiteESRepository testSuiteESRepository;
    private ProjectFileService projectFileService;
    private ProjectService projectService;

    @Autowired
    public TestSuiteServiceImpl(TestSuiteRepository repository, TestSuiteConverter converter, TestSuiteESRepository testSuiteESRepository,
                                ProjectFileService projectFileService, ProjectService projectService) {
        super(repository, converter);
        this.testSuiteESRepository = testSuiteESRepository;
        this.projectFileService = projectFileService;
        this.projectService = projectService;
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.TestSuite> save(com.fxlabs.fxt.dto.project.TestSuite testSuite, String user) {
        Optional<TestSuite> testSuiteOptional = ((TestSuiteRepository) repository).findByProjectIdAndName(testSuite.getProjectId(), testSuite.getName());

        TestSuite entity = null;
        if (testSuiteOptional.isPresent()) {
            entity = testSuiteOptional.get();
        }

        if (entity != null) {
            // copy id and version
            testSuite.setId(entity.getId());
            //testSuite.setVersion(entity.getVersion());
        }

        // type
        if (testSuite.getType() == null) {
            testSuite.setType(TestSuiteType.SUITE);
        }

        TestSuite ts = converter.convertToEntity(testSuite);
        entity = ((TestSuiteRepository) repository).save(ts);
        testSuiteESRepository.save(entity);

        // project_file
        this.projectFileService.saveFromTestSuite(testSuite, ts.getProjectId());

        return new Response<com.fxlabs.fxt.dto.project.TestSuite>(converter.convertToDto(entity));

    }

    @Override
    public void isUserEntitled(String id, String user) {
        Optional<TestSuite> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
        projectService.isUserEntitled(optional.get().getProjectId(), user);
    }
}
