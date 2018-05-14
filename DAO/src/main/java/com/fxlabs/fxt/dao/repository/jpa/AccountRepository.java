package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.clusters.AccountType;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByNameAndOrgId(String name, String orgId);

    Optional<Account> findByNameAndOrgName(String name, String orgName);

    Page<Account> findByCreatedBy(String owner, Pageable pageable);

    Long countByVisibility(ClusterVisibility visibility);

    List<Account> findByAccountTypeAndCreatedBy(AccountType type, String createdBy);

    //@Query("SELECT * FROM CloudAccount ca WHERE ca.accountType in ?1 AND ca.createdBy = ?2")
    List<Account> findByAccountTypeInAndCreatedBy(List<AccountType> types, String createdBy);

}
