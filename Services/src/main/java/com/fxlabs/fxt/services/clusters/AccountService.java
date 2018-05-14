package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 * @author Mohammed Shoukath Ali
 * @since 4/28/2018
 *
 */
public interface AccountService extends GenericService<Account, String> {

    Response<Account> create(Account dto, String user);

    Response<Account> update(Account dto, String user);

    Response<List<Account>> findAll(String user, Pageable pageable);

    Response<Account> findByName(String id, String user);

    Response<Account> findById(String id, String user);

    Response<Account> delete(String s, String user);

    Response<Long> countBotRegions(String user);

    public Response<List<Account>> findByAccountType(String accountType, String user);
}
