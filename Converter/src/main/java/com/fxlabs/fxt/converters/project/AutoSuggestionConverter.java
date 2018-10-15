package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.AutoSuggestion;
import com.fxlabs.fxt.dao.entity.project.Project;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Luqman Shareef
 */
@Mapper(componentModel = "spring")
public interface AutoSuggestionConverter extends BaseConverter<AutoSuggestion, com.fxlabs.fxt.dto.project.AutoSuggestion> {
}
