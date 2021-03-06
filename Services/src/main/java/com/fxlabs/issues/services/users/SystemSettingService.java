package com.fxlabs.issues.services.users;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.users.SystemSetting;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SystemSettingService extends GenericService<SystemSetting, String> {

    public String FX_HOST = "FX_HOST";
    public String FX_PORT = "FX_PORT";
    public String FX_SSL = "FX_SSL";
    public String FX_IAM = "FX_IAM";
    public String BOT_TAG = "BOT_TAG";

    public Response<Boolean> save(List<SystemSetting> dtos);

    public Response<SystemSetting> save(SystemSetting dto);

    public String findByKey(String key);

}
