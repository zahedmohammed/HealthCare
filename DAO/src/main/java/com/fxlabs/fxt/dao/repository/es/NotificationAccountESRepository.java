package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.notify.NotificationAccount;
import com.fxlabs.fxt.dao.entity.notify.NotificationVisibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
public interface NotificationAccountESRepository extends ElasticsearchRepository<NotificationAccount, String> {

    List<NotificationAccount> findByVisibility(NotificationVisibility visibility);
}
