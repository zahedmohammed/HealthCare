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

    Page<SkillSubscription> findByCreatedByAndInactive(String user, boolean inactive, Pageable pageable);

    Optional<SkillSubscription> findByOrgNameAndNameAndInactive(String org, String name, boolean inactive);

   // Page<SkillSubscription> findBySkillSkillTypeAndInactiveAndCreatedBy(SkillType skillType, boolean inactive, String user, Pageable pageable);

    Long countByCreatedByAndInactive(String user, boolean inactive);


}
