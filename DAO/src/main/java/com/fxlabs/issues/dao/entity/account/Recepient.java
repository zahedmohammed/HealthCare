package com.fxlabs.issues.dao.entity.account;

import com.fxlabs.issues.dao.entity.base.BaseEntity;
import com.fxlabs.issues.dao.entity.users.Users;
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
@EqualsAndHashCode(callSuper = false)
public class Recepient extends BaseEntity {

    private String name;
    private String email;
    private String phone;
    private String accountNumber;
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "users_id")
    Users user;

}
