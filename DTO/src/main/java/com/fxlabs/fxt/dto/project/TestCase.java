package com.fxlabs.fxt.dto.project;

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
public class TestCase {

    private Integer id;
    private String body;
    private Boolean inactive;

}

