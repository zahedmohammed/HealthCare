package com.fxlabs.fxt.dto.users;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.UserMinimalDto;
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
public class OrgUsers extends BaseDto<String> {

    private NameDto org;

    private UserMinimalDto users;

    private OrgRole orgRole; // USER | ADMIN

    private OrgUserStatus status; // INVITE_SENT | ACTIVE | DECLINED


}

