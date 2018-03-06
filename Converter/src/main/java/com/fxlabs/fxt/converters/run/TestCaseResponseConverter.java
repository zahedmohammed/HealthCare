package com.fxlabs.fxt.converters.run;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface TestCaseResponseConverter extends BaseConverter<TestCaseResponse, com.fxlabs.fxt.dto.run.TestCaseResponse> {
}
