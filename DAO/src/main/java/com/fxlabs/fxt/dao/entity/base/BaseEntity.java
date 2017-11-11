package com.fxlabs.fxt.dao.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
//import org.springframework.data.solr.core.mapping.SolrDocument;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@EqualsAndHashCode(of = {"id"})
public class BaseEntity<U> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(name = "created_by")
    //@CreatedBy
    private U createdBy;

    @Column(name = "created_date", nullable = false, updatable = false)
    //@CreatedDate
    private long createdDate;

    @Column(name = "modified_by")
    //@LastModifiedBy
    private U modifiedBy;

    @Column(name = "modified_date")
    //@LastModifiedDate
    private long modifiedDate;

}

