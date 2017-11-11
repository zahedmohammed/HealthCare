package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.DataSetConverter;
import com.fxlabs.fxt.converters.run.RunConverter;
import com.fxlabs.fxt.dao.entity.run.DataSet;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.DataSetRepository;
import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataSetServiceImpl extends GenericServiceImpl<DataSet, com.fxlabs.fxt.dto.run.DataSet, String> implements DataSetService {

    @Autowired
    public DataSetServiceImpl(DataSetRepository repository, DataSetConverter converter) {
        super(repository, converter);
    }

}
