package com.fxlabs.fxt.converters.skills;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.skills.SkillSubscription;
import com.fxlabs.fxt.dao.entity.skills.SubscriptionTask;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface SubscriptionTaskConverter extends BaseConverter<SubscriptionTask, com.fxlabs.fxt.dto.skills.SubscriptionTask> {
}
