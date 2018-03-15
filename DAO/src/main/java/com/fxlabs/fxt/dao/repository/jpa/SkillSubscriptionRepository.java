package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.skills.SkillSubscription;
import com.fxlabs.fxt.dao.entity.skills.SkillType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SkillSubscriptionRepository extends JpaRepository<SkillSubscription, String> {

    Page<SkillSubscription> findByCreatedBy(String user, Pageable pageable);

    Optional<SkillSubscription> findByOrgNameAndName(String org, String name);

    Page<SkillSubscription> findBySkillSkillTypeAndInactiveAndCreatedBy(SkillType skillType, boolean inactive, String user, Pageable pageable);

    Long countBySkillSkillTypeAndCreatedBy(SkillType skillType, String user);


}
