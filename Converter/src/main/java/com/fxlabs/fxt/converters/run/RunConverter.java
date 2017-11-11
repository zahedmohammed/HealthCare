package com.fxlabs.fxt.converters.run;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.run.Run;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RunConverter extends BaseConverter<Run, com.fxlabs.fxt.dto.run.Run> {
}
