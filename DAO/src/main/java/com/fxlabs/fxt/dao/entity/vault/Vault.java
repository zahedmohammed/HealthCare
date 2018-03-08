package com.fxlabs.fxt.dao.entity.vault;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    private String val;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private VaultVisibility visibility;

    @PrePersist
    @PreUpdate
    public void setVisi() {
        if (this.visibility == null) {
            this.visibility = VaultVisibility.PRIVATE;
        }
    }


}
