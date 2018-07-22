package com.fxlabs.fxt.dto.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Mohammed Shoukath Ali
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class TestSuitesDeletedDto {
    private static final long serialVersionUID = 1L;
    private String projectId;
    private Set<String> deletedFileNames;

}
