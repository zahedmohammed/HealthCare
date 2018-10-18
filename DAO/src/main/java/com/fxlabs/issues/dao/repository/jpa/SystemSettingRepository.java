package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.users.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SystemSettingRepository extends JpaRepository<SystemSetting, String> {
    Optional<SystemSetting> findByKey(String key);
}
