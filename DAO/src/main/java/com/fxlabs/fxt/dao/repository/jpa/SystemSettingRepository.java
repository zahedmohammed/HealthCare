package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SystemSettingRepository extends JpaRepository<SystemSetting, String> {
    Optional<SystemSetting> findByKey(String key);
}
