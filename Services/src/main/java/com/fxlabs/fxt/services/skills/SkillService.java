package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.skills.Skill;
import com.fxlabs.fxt.dto.skills.SkillType;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SkillService extends GenericService<Skill, String> {

    Response<String> findByName(String name);

    Response<List<Skill>> findByType(SkillType skillType, String user, Pageable pageable);
}
