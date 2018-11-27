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
public class Address extends BaseEntity {

    private String lattitude;
    private String longitude;
    private String doorNo;
    private String streetName;
    private String locality;
    private String city;

}
