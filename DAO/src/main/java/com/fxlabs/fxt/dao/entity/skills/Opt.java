package com.fxlabs.fxt.dao.entity.skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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

    @Column(name = "_order")
    private Integer order;
    private String label;
    @Column(name = "_value")
    private String value;
    private Boolean mandatory = false;

}

