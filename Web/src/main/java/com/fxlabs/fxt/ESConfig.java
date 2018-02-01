package com.fxlabs.fxt;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


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

