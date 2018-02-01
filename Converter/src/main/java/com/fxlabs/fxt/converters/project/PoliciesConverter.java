package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.Policies;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface PoliciesConverter extends BaseConverter<Policies, com.fxlabs.fxt.dto.project.Policies> {
}
