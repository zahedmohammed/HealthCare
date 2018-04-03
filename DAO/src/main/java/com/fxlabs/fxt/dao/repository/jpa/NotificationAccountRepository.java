package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.notify.NotificationAccount;
import com.fxlabs.fxt.dao.entity.notify.NotificationVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
public interface NotificationAccountRepository extends JpaRepository<NotificationAccount, String> {

    Optional<NotificationAccount> findByNameAndOrgId(String name, String orgId);

    Optional<NotificationAccount> findByNameAndOrgName(String name, String orgName);

    Page<NotificationAccount> findByCreatedBy(String owner, Pageable pageable);

    Long countByVisibility(NotificationVisibility visibility);

}
