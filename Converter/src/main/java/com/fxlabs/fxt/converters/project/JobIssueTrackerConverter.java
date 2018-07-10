package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.JobIssueTracker;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface JobIssueTrackerConverter extends BaseConverter<JobIssueTracker, com.fxlabs.fxt.dto.project.JobIssueTracker> {
}
