package com.fxlabs.fxt.dao.entity.users;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

/**
 * @author Intesar Shannan Mohammed
 */

@Document(indexName = "fx-org-users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrgUsers extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "users_id")
    private Users users;

    @Enumerated(EnumType.STRING)
    private OrgRole orgRole; // USER | ADMIN

    @Enumerated(EnumType.STRING)
    private OrgUserStatus status; // INVITE_SENT | ACTIVE | DECLINED


}

