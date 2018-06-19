package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarketplaceDataTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private String importName;
    private String projectId;
    private String eval;
    private String errors;

    // policies
    private String policy; // repeatAll
    private List<DataRecord> records;
    private Long totalElements = -1L;
    private int currentPage = -1;

}
