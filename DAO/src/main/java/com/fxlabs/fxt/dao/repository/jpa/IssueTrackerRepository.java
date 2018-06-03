package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.it.IssueTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface IssueTrackerRepository extends JpaRepository<IssueTracker, String> {

    Page<IssueTracker> findByOrgId(String org, Pageable pageable);

    Optional<IssueTracker> findByIdAndOrgId(String id, String org);

    Page<IssueTracker> findByCreatedByAndInactive(String user, boolean inactive, Pageable pageable);

    Page<IssueTracker> findByOrgIdAndInactive(String org, boolean inactive, Pageable pageable);

    Optional<IssueTracker> findByOrgNameAndNameAndInactive(String org, String name, boolean inactive);

   // Page<SkillSubscription> findBySkillSkillTypeAndInactiveAndCreatedBy(SkillType skillType, boolean inactive, String user, Pageable pageable);

    Long countByOrgIdAndInactive(String user, boolean inactive);

    List<IssueTracker> findByAccountIdAndInactive(String id, boolean inactive);


}
