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
public class ProjectMinimalDto extends IdDto {
    private static final long serialVersionUID = 1L;
    private String name;
    private NameDto org;

}
