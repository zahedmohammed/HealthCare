package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.skills.Skill;
import com.fxlabs.fxt.dao.entity.skills.SkillType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SkillRepository extends JpaRepository<Skill, String> {

    Optional<Skill> findByOrgNameAndName(String org, String name);

    Page<Skill> findBySkillType(SkillType skillType, org.springframework.data.domain.Pageable pageable);
}
