package com.fxlabs.fxt.dto.users;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


//@SolrDocument(collection = "fx")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectUsers extends BaseDto<String> {

    private Project project;

    private Users users;

    private ProjectRole role; // READ | WRITE | OWNER

}

