package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.notify.Notification;
import com.fxlabs.fxt.dao.entity.notify.NotificationVisibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
public interface NotificationESRepository extends ElasticsearchRepository<Notification, String> {

    List<Notification> findByVisibility(NotificationVisibility visibility);
}
