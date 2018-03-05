package com.fxlabs.fxt.converters.skills;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.skills.Skill;
import com.fxlabs.fxt.dao.entity.users.Org;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface SkillConverter extends BaseConverter<Skill, com.fxlabs.fxt.dto.skills.Skill> {
}
