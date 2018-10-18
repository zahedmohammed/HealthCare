package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.users.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface AccessKeyRepository extends JpaRepository<AccessKey, String> {

    Optional<AccessKey> findByAccessKeyAndExpirationAfter(String name, Date expiration);
}
