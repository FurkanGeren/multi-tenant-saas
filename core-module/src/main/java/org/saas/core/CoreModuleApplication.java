package org.saas.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.saas.core.config")
public class CoreModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreModuleApplication.class, args);
    }

}
