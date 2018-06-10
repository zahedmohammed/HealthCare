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

    Optional<Account> findByNameAndOrgIdAndInactive(String name, String orgId, boolean inactive);

    Optional<Account> findByNameAndOrgName(String name, String orgName);

    Page<Account> findByCreatedBy(String owner, Pageable pageable);

    Page<Account> findByOrgIdAndInactive(String orgId, boolean inactive, Pageable pageable);

    List<Account> findByAccountTypeAndCreatedBy(AccountType type, String createdBy);

    List<Account> findByAccountTypeInAndCreatedBy(List<AccountType> types, String createdBy);

    List<Account> findByAccountTypeInAndOrgIdAndInactive(List<AccountType> types, String orgId, boolean inactive);

    Optional<Account> findByIdAndOrgId(String id, String orgId);

}
