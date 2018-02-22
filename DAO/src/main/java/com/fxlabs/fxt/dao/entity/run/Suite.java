package com.fxlabs.fxt.dao.entity.run;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Intesar Shannan Mohammed
 */

@Document(indexName = "fxsuiteresponse")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Suite extends BaseEntity {

    private String runId;
    private String suiteName;

    private Long tests = new Long(0);
    private Long failed = new Long(0);
    @Column(name = "size_")
    private Long size = new Long(0);
    @Column(name = "time_")
    private Long time = new Long(0);

}

