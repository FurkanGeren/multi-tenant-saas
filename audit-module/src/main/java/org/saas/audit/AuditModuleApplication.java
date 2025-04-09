package org.saas.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "org.saas.core",
        "org.saas.audit"
})
@EnableJpaAuditing
@EnableJpaRepositories
public class AuditModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditModuleApplication.class, args);
    }

}
