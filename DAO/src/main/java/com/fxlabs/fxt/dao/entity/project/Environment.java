package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Environment extends BaseEntity {


    private String name;
    private String refId;
    private String description;

    private String baseUrl;

    @ElementCollection
    private List<Auth> auths;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id")
    private Project project;


    @PrePersist
    public void preCreate() {
        if (StringUtils.isEmpty(refId)) {
            refId = name;
        }
    }
}

