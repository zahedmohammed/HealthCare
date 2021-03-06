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
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SavingsAccount extends BaseEntity {

    private int accountNumber;
    private BigDecimal accountBalance;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "users_id")
    Users user;


}
