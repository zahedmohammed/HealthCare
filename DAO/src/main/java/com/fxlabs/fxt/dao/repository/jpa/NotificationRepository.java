package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.notify.Notification;
import com.fxlabs.fxt.dao.entity.notify.NotificationVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 * @author Mohammed Shoukath Ali
 */
public interface NotificationRepository extends JpaRepository<Notification, String> {

    Optional<Notification> findByNameAndOrgId(String name, String orgId);

    Optional<Notification> findByIdAndOrgId(String id, String orgId);

    Optional<Notification> findByNameAndOrgName(String name, String orgName);

    Page<Notification> findByOrgId(String o, Pageable pageable);

    Long countByVisibility(NotificationVisibility visibility);

    Long countByCreatedByAndInactive(String user, boolean inactive);

    List<Notification> findByAccountIdAndInactive(String id, boolean inactive);

}
