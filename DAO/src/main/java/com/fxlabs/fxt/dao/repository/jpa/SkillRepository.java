package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.skills.Skill;
import com.fxlabs.fxt.dao.entity.skills.SkillSubscription;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SkillRepository extends JpaRepository<Skill, String> {

    Optional<Skill> findByOrgNameAndName(String org, String name);
}
