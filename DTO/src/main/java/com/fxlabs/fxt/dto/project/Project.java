package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseDto<String> {


    private NameDto org;
    private String name;
    private String description;
    private Date lastSync;

    private List<String> licenses;

    //private List<Environment> environments;

    //private List<Job> jobs;

    public static final String FILE_NAME = "FILE_NAME";
    public static final String FILE_CONTENT = "FILE_CONTENT";
    public static final String MODIFIED_DATE = "MODIFIED_DATE";
    private Map<String, String> props = new HashMap<>();

}

