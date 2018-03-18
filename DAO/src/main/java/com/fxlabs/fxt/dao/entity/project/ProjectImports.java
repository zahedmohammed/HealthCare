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
import java.util.Map;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fxprojectimports")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectImports extends BaseEntity {

    private String projectId;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "val")
    @CollectionTable(name = "project_imports_map", joinColumns = @JoinColumn(name = "project_imports_id"))
    private Map<String, String> imports;

}

