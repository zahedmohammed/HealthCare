package com.fxlabs.fxt.dao.entity.users;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Org extends BaseEntity<String> {

    private String name;
    private String description;

    private String type; // PERSONAL | TEAM | ENTERPRISE
    private String billingEmail;

    private String company;
    private String location;

    private String plan; // FREE | PRO | UNLIMITED | PAY-AS-YOU-GO

    // TODO Billing Info

}

