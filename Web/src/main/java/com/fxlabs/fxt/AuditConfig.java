package com.fxlabs.fxt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Intesar Shannan Mohammed
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    AuditorAwareImpl auditorAware() {
        return new AuditorAwareImpl();
    }

}

