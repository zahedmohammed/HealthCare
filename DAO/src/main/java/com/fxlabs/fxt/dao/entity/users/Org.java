package com.fxlabs.fxt.dao.entity.users;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Intesar Shannan Mohammed
 */

@Document(indexName = "fx-orgs")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Org extends BaseEntity {

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private OrgType orgType; // PERSONAL | TEAM | ENTERPRISE
    private String billingEmail;

    private String company;
    private String location;

    @Enumerated(EnumType.STRING)
    private OrgPlan orgPlan; // FREE | PRO | UNLIMITED | PAY-AS-YOU-GO

    // TODO Billing Info

}

