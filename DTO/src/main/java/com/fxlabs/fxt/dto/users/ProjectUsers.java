package com.fxlabs.fxt.dto.users;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
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
public class ProjectUsers extends BaseDto<String> {

    private ProjectMinimalDto project;

    private UserMinimalDto users;

    private ProjectRole role; // READ | WRITE | OWNER

}

