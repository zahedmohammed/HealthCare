package com.fxlabs.fxt.dao.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fxprojectfile")
@JsonIgnoreProperties( value = {"content"} )
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

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

}

