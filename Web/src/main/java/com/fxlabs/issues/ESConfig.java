package com.fxlabs.issues;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Intesar Shannan Mohammed
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.fxlabs.issues.dao.repository.es")
public class ESConfig {

    /*@Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "fxcluster").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("server"), 9300));

        return new ElasticsearchTemplate(client);
    }*/
}

