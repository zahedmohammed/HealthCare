package com.fxlabs.issues.services.users;

import com.fxlabs.issues.converters.users.SystemSettingConverter;
import com.fxlabs.issues.dao.repository.jpa.SystemSettingRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.users.SystemSetting;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class SystemSettingServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.users.SystemSetting, SystemSetting, String> implements SystemSettingService {

    private SystemSettingRepository systemSettingRepository;
    private SystemSettingConverter systemSettingConverter;
    private TextEncryptor encryptor;
    private String ENCRYPTED_PREFIX = "encrypted:";

    @Autowired
    public SystemSettingServiceImpl(SystemSettingRepository systemSettingRepository, SystemSettingConverter systemSettingConverter, TextEncryptor encryptor) {
        super(systemSettingRepository, systemSettingConverter);

        this.systemSettingRepository = systemSettingRepository;
        this.systemSettingConverter = systemSettingConverter;
        this.encryptor = encryptor;

    }

    public Response<List<SystemSetting>> findAll(String user, Pageable pageable) {
        Page<com.fxlabs.issues.dao.entity.users.SystemSetting> page = systemSettingRepository.findAll(pageable);
        return new Response<List<SystemSetting>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    public Response<Boolean> save(List<SystemSetting> dtos) {
        dtos.forEach(dto -> {
            save(dto);
        });
        return new Response<>(true);
    }

    public Response<SystemSetting> save(SystemSetting dto) {

        if (org.apache.commons.lang3.StringUtils.isEmpty(dto.getKey())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid key"));
        }

        if (org.apache.commons.lang3.StringUtils.isEmpty(dto.getValue())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid value"));
        }

        // let's encrypt key bot.pass
        if (StringUtils.equals(dto.getKey(), "FX_IAM") && !StringUtils.startsWith(dto.getValue(), ENCRYPTED_PREFIX)) {
            dto.setValue(ENCRYPTED_PREFIX + encryptor.encrypt(dto.getValue()));
        }
        return save(dto, null);
    }

    public String findByKey(String key) {
        Optional<com.fxlabs.issues.dao.entity.users.SystemSetting> systemSettingOptional = systemSettingRepository.findByKey(key);
        String val = systemSettingOptional.get().getValue();

        String value = val;
        if (StringUtils.startsWith(val, ENCRYPTED_PREFIX)) {
            value = StringUtils.removeStart(val, ENCRYPTED_PREFIX);
        }

        return value;

    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }


}
