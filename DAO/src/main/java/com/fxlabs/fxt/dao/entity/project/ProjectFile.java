package com.fxlabs.fxt.dao.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

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
public class ProjectFile extends BaseEntity {

    //@ManyToOne(cascade = CascadeType.REFRESH)
    //@JoinColumn(name = "project_id")
    private String projectId;

    private String filename;

    private String checksum;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

}

