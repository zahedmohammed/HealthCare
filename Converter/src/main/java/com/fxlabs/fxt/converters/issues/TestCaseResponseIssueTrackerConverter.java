package com.fxlabs.fxt.converters.issues;

import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TestCaseResponseIssueTrackerConverter implements BaseConverter<TestCaseResponseIssueTracker, com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker> {
}
