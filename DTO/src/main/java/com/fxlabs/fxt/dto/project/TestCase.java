package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Intesar Shannan Mohammed
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestCase implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String body;
    private Boolean inactive = false;

}

