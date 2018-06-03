package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.SystemSetting;
import com.fxlabs.fxt.services.base.GenericService;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SystemSettingService extends GenericService<SystemSetting, String> {

    public String FX_IAM = "FX_IAM";
    public String BOT_TAG = "BOT_TAG";

    public Response<Boolean> save(List<SystemSetting> dtos);

    public Response<SystemSetting> save(SystemSetting dto);

    public Response<SystemSetting> findByKey(String key);
}
