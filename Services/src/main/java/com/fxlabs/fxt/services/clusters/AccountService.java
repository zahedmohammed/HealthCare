package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @author Mohammed Shoukath Ali
 * @since 3/20/2018
 * @since 4/28/2018
 */
public interface AccountService extends GenericService<Account, String> {

    public Response<List<Account>> findAll(String org, Pageable pageable);

    Response<Account> create(Account dto, String org);

    Response<Account> update(Account dto, String org, String user);

    Response<Account> findByName(String id, String org);

    Response<Account> findById(String id, String orgId);

    Response<Account> delete(String s, String org, String user);

    public Response<List<Account>> findByAccountType(String accountType, String org);

    Response<List<Account>> search(String keyword, String orgId, Pageable pageable);
}
