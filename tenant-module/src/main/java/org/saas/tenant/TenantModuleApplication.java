package org.saas.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.saas")
@EnableJpaAuditing
@EntityScan(basePackages = {
        "org.saas.tenant.entity",
        "org.saas.core.domain"
})
public class TenantModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenantModuleApplication.class, args);
    }

}
