package com.fxlabs.fxt.converters.run;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataSetConverter extends BaseConverter<TestSuiteResponse, com.fxlabs.fxt.dto.run.DataSet> {
}
