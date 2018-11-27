package com.fxlabs.issues.dao.entity.branch;

import com.fxlabs.issues.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Branch extends BaseEntity {

    private String access;
    private String atmAtBranch;
    private String branchName;
    private String branchMediatedServiceName;
    private String branchType;
    private String customerSegment;
    private String faxNumber;

    Address address;


}
