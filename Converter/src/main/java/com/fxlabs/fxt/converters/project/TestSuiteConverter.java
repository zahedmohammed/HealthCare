package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface TestSuiteConverter extends BaseConverter<TestSuite, com.fxlabs.fxt.dto.project.TestSuite> {
}
