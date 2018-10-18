package com.fxlabs.issues.dto.users;

import com.fxlabs.issues.dto.base.BaseDto;
import com.fxlabs.issues.dto.base.UserMinimalDto;
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
public class UsersPassword extends BaseDto<String> {

    private UserMinimalDto users;

    private String password;

    //private String grantKey;

    private boolean active;

}

