package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.SystemSettingConverter;
import com.fxlabs.fxt.dao.repository.jpa.SystemSettingRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class SystemSettingServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.users.SystemSetting, com.fxlabs.fxt.dto.users.SystemSetting, String> implements SystemSettingService {

    private SystemSettingRepository systemSettingRepository;
    private SystemSettingConverter systemSettingConverter;

    @Autowired
    public SystemSettingServiceImpl(SystemSettingRepository systemSettingRepository, SystemSettingConverter systemSettingConverter) {
        super(systemSettingRepository, systemSettingConverter);

        this.systemSettingRepository = systemSettingRepository;
        this.systemSettingConverter = systemSettingConverter;

    }


}
