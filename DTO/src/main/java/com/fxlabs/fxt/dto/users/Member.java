package com.fxlabs.fxt.dto.users;

import com.fxlabs.fxt.dto.base.BaseDto;
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
public class Member extends BaseDto<String> {

    private String orgId;

    private String name;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    private OrgRole orgRole; // USER | ADMIN

}

