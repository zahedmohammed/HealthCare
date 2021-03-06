package com.fxlabs.issues.dto.users;

import com.fxlabs.issues.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemSetting extends BaseDto<String> {

    private String key;
    private String label;
    private String value;

}

