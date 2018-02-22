package com.fxlabs.fxt.converters.run;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.run.Suite;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface SuiteConverter extends BaseConverter<Suite, com.fxlabs.fxt.dto.run.Suite> {
}
