package org.saas.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {
        "org.saas.core",
        "org.saas.audit"
}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org\\.saas\\.core\\.security\\..*"))
public class AuditModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditModuleApplication.class, args);
    }

}
