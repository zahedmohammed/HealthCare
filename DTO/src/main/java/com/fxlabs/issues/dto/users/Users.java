package com.fxlabs.issues.dto.users;

import com.fxlabs.issues.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseDto<String> {


    private String name;
    private String username;
    private String email;
    private String password;
    private String company;
    private String location;
    private String jobTitle;
    //private Org org;

    private List<String> privileges;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;


}

