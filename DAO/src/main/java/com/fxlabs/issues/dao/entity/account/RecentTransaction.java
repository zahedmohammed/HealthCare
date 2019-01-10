package com.fxlabs.issues.dao.entity.account;

import com.fxlabs.issues.dao.entity.base.BaseEntity;
import com.fxlabs.issues.dao.entity.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class RecentTransaction extends BaseEntity {

    private String location;
    private String description;
    private boolean confirmed;
   // private String contactName;
    @Enumerated (EnumType.STRING)
    private TransactionType transactionType;
//    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "user_id")
//    Users user;
}
