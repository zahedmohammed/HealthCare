package com.fxlabs.issues.dto.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserMinimalDto extends IdDto {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String username;
    private String company;
    private String location;
    private String jobTitle;
}
