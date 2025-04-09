package org.saas.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {
        "org.saas.core",
        "org.saas.order"
})
@EnableJpaAuditing
@EnableJpaRepositories
public class OrderModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderModuleApplication.class, args);
    }

}
