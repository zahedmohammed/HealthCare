package com.fxlabs.fxt.dao.entity.users;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrgUsers extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="org_id")
    private Org org;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="users_id")
    private Users users;

    private String role; // USER | ADMIN

    private String status; // INVITE_SENT | ACTIVE | DECLINED


}

