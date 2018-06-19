package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BasicBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Mohammed Shoukath Ali
 */

@Document(indexName = "fx-datarecords")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DataRecord extends BasicBaseEntity {
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String record;
    private String dataSet;

}

