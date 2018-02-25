package com.fxlabs.fxt.dao.entity.project;

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
public class TestCase {

    private Integer id;
    private String body;
    private Boolean inactive = false;

}

