package com.fxlabs.fxt.dao.entity.skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * @author Intesar Shannan Mohammed
 */

@Embeddable
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

