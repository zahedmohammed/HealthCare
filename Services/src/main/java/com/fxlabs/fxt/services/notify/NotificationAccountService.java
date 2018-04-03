package com.fxlabs.fxt.services.notify;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notify.NotificationAccount;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 *
 */
public interface NotificationAccountService extends GenericService<NotificationAccount, String> {

    Response<NotificationAccount> create(NotificationAccount dto, String user);

    Response<NotificationAccount> update(NotificationAccount dto, String user);

    Response<List<NotificationAccount>> findAll(String user, Pageable pageable);

    Response<NotificationAccount> findByName(String id, String user);

    Response<NotificationAccount> findById(String id, String user);

    Response<NotificationAccount> delete(String s, String user);
}
