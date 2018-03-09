package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.skills.Skill;
import com.fxlabs.fxt.dto.skills.SkillSubscription;
import com.fxlabs.fxt.services.base.GenericService;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SkillSubscriptionService extends GenericService<SkillSubscription, String> {

    Response<SkillSubscription> findByName(String name);
}
