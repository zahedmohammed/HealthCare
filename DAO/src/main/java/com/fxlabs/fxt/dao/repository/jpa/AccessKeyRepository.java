package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.AccessKey;
import com.fxlabs.fxt.dao.entity.users.UsersPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface AccessKeyRepository extends JpaRepository<AccessKey, String> {

    Optional<AccessKey> findByAccessKeyAndExpirationAfter(String name, Date expiration);
}
