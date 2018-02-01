package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Job extends BaseEntity {

    private String name;
    private String refId;
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id")
    private Project project;

    //@ManyToOne(cascade = CascadeType.REFRESH)
    private String environment;

    @ElementCollection
    private List<String> tags;

    private String regions;

    @PrePersist
    public void preCreate() {
        if (StringUtils.isEmpty(refId)) {
            refId = name;
        }
    }


}

