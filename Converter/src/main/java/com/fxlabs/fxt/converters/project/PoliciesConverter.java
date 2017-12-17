package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.Policies;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PoliciesConverter extends BaseConverter<Policies, com.fxlabs.fxt.dto.project.Policies> {
}
