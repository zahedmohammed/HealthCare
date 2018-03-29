package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.dto.skills.Skill;
import com.fxlabs.fxt.dto.skills.SkillSubscription;
import com.fxlabs.fxt.dto.skills.SkillType;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SkillSubscriptionService extends GenericService<SkillSubscription, String> {

    Response<SkillSubscription> findByName(String name);

    Response<List<SkillSubscription>> findBySkillType(String skillType, String user, Pageable pageable);

    Response<SkillSubscription> addITBot(SkillSubscription dto, String user);

    Response<SkillSubscription> deleteITBot(String id, String user);

    Response<SkillSubscription> addExecBot(Cluster dto, String user);

    Response<SkillSubscription> deleteExecBot(Cluster dto, String user);

    Response<Long> countBySkillType(String user, SkillType skillType);
}
