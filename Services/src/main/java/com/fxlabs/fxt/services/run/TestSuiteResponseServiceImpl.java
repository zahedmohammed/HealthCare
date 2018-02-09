package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.TestSuiteResponseConverter;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteResponseRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class TestSuiteResponseServiceImpl extends GenericServiceImpl<TestSuiteResponse, com.fxlabs.fxt.dto.run.TestSuiteResponse, String> implements TestSuiteResponseService {

    @Autowired
    public TestSuiteResponseServiceImpl(TestSuiteResponseRepository repository, TestSuiteResponseConverter converter) {
        super(repository, converter);
    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }
}
