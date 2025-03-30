package org.saas.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
        "org.saas.audit",
        "org.saas.core"
})
@EnableJpaAuditing
@ComponentScan(basePackages = {"org.saas.core", "org.saas.audit"})
public class AuditModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditModuleApplication.class, args);
    }

}
