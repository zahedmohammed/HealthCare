package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.SystemSettingConverter;
import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import com.fxlabs.fxt.dao.repository.jpa.ClusterRepository;
import com.fxlabs.fxt.dao.repository.jpa.SystemSettingRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Saving;
import com.fxlabs.fxt.dto.users.SystemSetting;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.util.text.TextEncryptor;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
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
public class SystemSettingServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.users.SystemSetting, com.fxlabs.fxt.dto.users.SystemSetting, String> implements SystemSettingService {

    private SystemSettingRepository systemSettingRepository;
    private SystemSettingConverter systemSettingConverter;
    private TextEncryptor encryptor;
    private String ENCRYPTED_PREFIX = "encrypted:";
    private ClusterRepository clusterRepository;

    @Autowired
    public SystemSettingServiceImpl(SystemSettingRepository systemSettingRepository, SystemSettingConverter systemSettingConverter, TextEncryptor encryptor,
                                    ClusterRepository clusterRepository) {
        super(systemSettingRepository, systemSettingConverter);

        this.systemSettingRepository = systemSettingRepository;
        this.systemSettingConverter = systemSettingConverter;
        this.clusterRepository = clusterRepository;
        this.encryptor = encryptor;

    }

    public Response<List<SystemSetting>> findAll(String user, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.users.SystemSetting> page = systemSettingRepository.findAll(pageable);
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
        Optional<com.fxlabs.fxt.dao.entity.users.SystemSetting> systemSettingOptional = systemSettingRepository.findByKey(key);
        String val = systemSettingOptional.get().getValue();

        String value = val;
        if (StringUtils.startsWith(val, ENCRYPTED_PREFIX)) {
            value = StringUtils.removeStart(val, ENCRYPTED_PREFIX);
        }

        return value;

    }

    @Override
    public Response<Saving> getSavingsById(String id) {

        if (org.apache.commons.lang3.StringUtils.isEmpty(id)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid id"));
        }

        Optional<Cluster> clusterResponse = clusterRepository.findById(id);

        if (!clusterResponse.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid id"));
        }

        Cluster entity = clusterResponse.get();

        String licenseSetting = findByKey("LICENSE_SAVING");
        String managedInstanceSetting = findByKey("MANAGED_INSTANCE_SAVING");

        Saving saving = new Saving();
        saving.setStartDate(entity.getCreatedDate());

        DateTime dt = new DateTime(entity.getCreatedDate());
        LocalDate dateToReturn = new LocalDate(entity.getCreatedDate());
        int calMonths = monthsBetweenIgnoreDays(new LocalDate(entity.getCreatedDate()), LocalDate.now());

        saving.setCalMonths(calMonths);
        saving.setCount(entity.getMin());

        int licenseSaving = calMonths * entity.getMin() * Integer.parseInt(licenseSetting);
        int managedInstanceSaving = calMonths * entity.getMin() * Integer.parseInt(licenseSetting);

        saving.setLicenseSaving(licenseSaving);
        saving.setManagedInstanceSaving(managedInstanceSaving);

        saving.setTotal(licenseSaving + managedInstanceSaving);

        return new Response<Saving>(saving);
    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }


    private  int monthsBetweenIgnoreDays(LocalDate start, LocalDate end) {
        start = start.withDayOfMonth(1);
        end = end.withDayOfMonth(1);
        return Months.monthsBetween(start, end).getMonths();
    }

}
