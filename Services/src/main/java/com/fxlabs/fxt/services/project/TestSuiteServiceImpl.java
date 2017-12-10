package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.TestSuiteConverter;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.repository.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TestSuiteServiceImpl extends GenericServiceImpl<TestSuite, com.fxlabs.fxt.dto.project.TestSuite, String> implements TestSuiteService {

    @Autowired
    public TestSuiteServiceImpl(TestSuiteRepository repository, TestSuiteConverter converter) {
        super(repository, converter);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.TestSuite> save(com.fxlabs.fxt.dto.project.TestSuite testSuite) {
        Optional<TestSuite> projectOptional = ((TestSuiteRepository) repository).findByProjectIdAndName(testSuite.getProject().getId(), testSuite.getName());

        TestSuite entity = null;
        if (projectOptional.isPresent()) {
            entity = projectOptional.get();
        }

        if (entity != null) {
            // copy id and version
            testSuite.setId(entity.getId());
            testSuite.setVersion(entity.getVersion());
        }


        entity = repository.save(converter.convertToEntity(testSuite));
        return new Response<com.fxlabs.fxt.dto.project.TestSuite>(converter.convertToDto(entity));

    }

}
