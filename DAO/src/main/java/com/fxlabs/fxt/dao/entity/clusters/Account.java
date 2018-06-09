package com.fxlabs.fxt.dao.entity.clusters;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
@Document(indexName = "fx-accounts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String region;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String accessKey;
    private String secretKey;

    private String prop1;
    private String prop2;
    private String prop3;

    @ElementCollection
    private List<String> allowedRegions;

}
