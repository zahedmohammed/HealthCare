package com.fxlabs.fxt;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.InetAddress;




@Configuration
@EnableElasticsearchRepositories(basePackages = "com.fxlabs.fxt.dao.repository.es")
public class ESConfig {

    /*@Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "fxcluster").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("server"), 9300));

        return new ElasticsearchTemplate(client);
    }*/
}

