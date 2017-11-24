package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.DataSetConverter;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import com.fxlabs.fxt.dao.repository.DataSetRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataSetServiceImpl extends GenericServiceImpl<TestSuiteResponse, com.fxlabs.fxt.dto.run.DataSet, String> implements DataSetService {

    @Autowired
    public DataSetServiceImpl(DataSetRepository repository, DataSetConverter converter) {
        super(repository, converter);
    }

}
