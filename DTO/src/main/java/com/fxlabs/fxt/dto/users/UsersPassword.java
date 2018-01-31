package com.fxlabs.fxt.dto.users;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import com.fxlabs.fxt.dto.base.UserMinimalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


//@SolrDocument(collection = "fx")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UsersPassword extends BaseDto<String> {

    private UserMinimalDto users;

    private String password;

    private boolean active;

}

