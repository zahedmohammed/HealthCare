package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.CloudAccount;
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
public interface CloudAccountService extends GenericService<CloudAccount, String> {

    Response<CloudAccount> create(CloudAccount dto, String user);

    Response<CloudAccount> update(CloudAccount dto, String user);

    Response<List<CloudAccount>> findAll(String user, Pageable pageable);

    Response<CloudAccount> findByName(String id, String user);

    Response<CloudAccount> findById(String id, String user);

    Response<CloudAccount> delete(String s, String user);

    Response<Long> countBotRegions(String user);

    public Response<List<CloudAccount>> findByAccountType(String accountType, String user);
}
