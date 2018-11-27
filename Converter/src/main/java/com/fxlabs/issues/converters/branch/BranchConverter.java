package com.fxlabs.issues.converters.branch;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.branch.Branch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchConverter extends BaseConverter<Branch, com.fxlabs.issues.dto.branch.Branch> {
}
