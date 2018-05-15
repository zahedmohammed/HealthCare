package com.fxlabs.fxt.services.notify;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notify.Notification;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 * @author Mohammed Shoukath Ali
 *
 */
public interface NotificationService extends GenericService<Notification, String> {

    Response<Notification> create(Notification dto, String org, String user);

    Response<Notification> update(Notification dto, String org, String user);

    Response<List<Notification>> findAll(String user, Pageable pageable);

    Response<Notification> findByName(String id, String user);

    Response<Notification> findById(String id, String user);

    Response<Notification> delete(String s, String org, String user);

    Response<Long> count(String user);

}
