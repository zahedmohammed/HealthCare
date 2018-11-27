package com.fxlabs.issues.dao.entity.transaction;

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
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SavingsTransaction extends BaseEntity {

    private String description;
    private String type;
    private String status;
    private double amount;
    private BigDecimal availableBalance;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "users_id")
    Users user;
}
