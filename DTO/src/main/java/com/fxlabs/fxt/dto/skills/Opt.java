package com.fxlabs.fxt.dto.skills;

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
@EqualsAndHashCode(callSuper = false)
public class Opt {

    private Integer id;
    private String label;
    private String value;
    private Boolean mandatory = false;

}

