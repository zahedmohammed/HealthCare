package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectCredential;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectCredentialConverter extends BaseConverter<ProjectCredential, com.fxlabs.fxt.dto.project.ProjectCredential> {
}
