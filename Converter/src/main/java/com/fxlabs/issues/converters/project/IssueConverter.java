package com.fxlabs.issues.converters.project;


import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.project.Issue;
import com.fxlabs.issues.dao.entity.project.Project;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface IssueConverter extends BaseConverter<Issue, com.fxlabs.issues.dto.project.Issue> {
}
