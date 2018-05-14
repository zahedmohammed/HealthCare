package com.fxlabs.fxt.converters.skills;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.it.IssueTracker;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface IssueTrackerConverter extends BaseConverter<IssueTracker, com.fxlabs.fxt.dto.it.IssueTracker> {
}
