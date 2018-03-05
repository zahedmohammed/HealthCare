package com.fxlabs.fxt.converters.skills;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.skills.SkillSubscription;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface SkillSubscriptionConverter extends BaseConverter<SkillSubscription, com.fxlabs.fxt.dto.skills.SkillSubscription> {
}
