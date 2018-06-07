package com.fxlabs.fxt.dao.entity.vault;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Vault extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    @Column(name = "_key")
    private String key;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String val;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

}
