package com.fxlabs.issues.dao.entity.account;

import com.fxlabs.issues.dao.entity.base.BaseEntity;
import com.fxlabs.issues.dao.entity.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PrimaryAccount extends BaseEntity {
    //@Min(10)
    private long accountNumber;
    private long accountBalance;
    @Enumerated (EnumType.STRING)
    private AccountType accountType; //Saving | Current

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    Users user;


}
