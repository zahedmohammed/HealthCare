package com.fxlabs.fxt.dao.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fxprojectfile")
@JsonIgnoreProperties(value = {"content"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectGitAccount extends BaseEntity {

    private String projectId;

    private String url;
    private String branch;
    private String username;
    private String password;
    private String lastCommit;

}

