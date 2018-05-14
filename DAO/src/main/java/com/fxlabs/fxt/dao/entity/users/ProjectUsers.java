package com.fxlabs.fxt.dao.entity.users;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fx-project-users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectUsers extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "users_id")
    private Users users;

    @Enumerated(EnumType.STRING)
    private ProjectRole role; // READ | WRITE | OWNER

}

