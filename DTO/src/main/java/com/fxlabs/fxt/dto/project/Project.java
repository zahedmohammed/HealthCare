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


    public static final String FILE_NAME = "FILE_NAME";
    public static final String FILE_CONTENT = "FILE_CONTENT";
    public static final String MODIFIED_DATE = "MODIFIED_DATE";
    public static final String MD5_HEX = "MD5_HEX";
    private NameDto org;

    //private List<Environment> environments;

    //private List<Job> jobs;
    private String name;
    private String description;
    private Date lastSync;
    private List<String> licenses;
    private Map<String, String> props = new HashMap<>();

}

